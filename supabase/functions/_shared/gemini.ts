const GEMINI_API_KEY = Deno.env.get("GEMINI_API_KEY") ?? "";
const GEMINI_MODEL = "gemini-2.5-flash-image";
const GEMINI_URL =
  `https://generativelanguage.googleapis.com/v1beta/models/${GEMINI_MODEL}:generateContent?key=${GEMINI_API_KEY}`;

/** Call Gemini with an image + prompt. Returns raw PNG bytes. */
export async function geminiEditImage(
  imageBytes: ArrayBuffer,
  mimeType: string,
  prompt: string,
): Promise<ArrayBuffer> {
  // Encode image to base64 in chunks to avoid stack overflow on large images
  const uint8 = new Uint8Array(imageBytes);
  let raw = "";
  const CHUNK = 8192;
  for (let i = 0; i < uint8.length; i += CHUNK) {
    raw += String.fromCharCode(...uint8.subarray(i, i + CHUNK));
  }
  const base64 = btoa(raw);

  const body = {
    contents: [{
      parts: [
        { text: prompt },
        { inlineData: { mimeType, data: base64 } },
      ],
    }],
    generationConfig: { responseModalities: ["TEXT", "IMAGE"] },
  };

  const resp = await fetch(GEMINI_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body),
  });

  if (!resp.ok) {
    const errText = await resp.text();
    console.error("Gemini error:", resp.status, errText);
    throw new Error(`Gemini API error: ${resp.status}`);
  }

  return extractImageFromResponse(await resp.json()) ?? imageBytes;
}

/** Call Gemini with text-only prompt to generate an image. Returns raw PNG bytes. */
export async function geminiGenerateImage(
  prompt: string,
): Promise<ArrayBuffer> {
  const body = {
    contents: [{ parts: [{ text: prompt }] }],
    generationConfig: { responseModalities: ["TEXT", "IMAGE"] },
  };

  const resp = await fetch(GEMINI_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body),
  });

  if (!resp.ok) {
    const errText = await resp.text();
    console.error("Gemini error:", resp.status, errText);
    throw new Error(`Gemini API error: ${resp.status}`);
  }

  const result = extractImageFromResponse(await resp.json());
  if (!result) throw new Error("Gemini returned no image for generation request");
  return result;
}

// deno-lint-ignore no-explicit-any
function getInlineData(p: any): { data: string; mimeType: string } | null {
  // Handle both camelCase (REST response) and snake_case (docs examples)
  return p.inlineData ?? p.inline_data ?? null;
}

function extractImageFromResponse(json: Record<string, unknown>): ArrayBuffer | null {
  // deno-lint-ignore no-explicit-any
  const parts = (json as any)?.candidates?.[0]?.content?.parts;
  if (!parts?.length) {
    throw new Error("No content in Gemini response");
  }

  // deno-lint-ignore no-explicit-any
  const imagePart = parts.find((p: any) => getInlineData(p)?.data);
  if (!imagePart) {
    // For edit operations: no image means nothing to change — caller returns original
    // For generate operations: this is a real error
    console.warn("Gemini returned no image, only text");
    return null;
  }

  const b64: string = getInlineData(imagePart)!.data;
  const binary = atob(b64);
  const bytes = new Uint8Array(binary.length);
  for (let i = 0; i < binary.length; i++) {
    bytes[i] = binary.charCodeAt(i);
  }
  return bytes.buffer;
}
