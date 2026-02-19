package com.tonychat.ai.provider

import android.util.Base64
import com.tonychat.ai.ImageGenerationProvider
import com.tonychat.ai.ImageGenerationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Gemini 2.5 Flash Image provider for emoji generation.
 * Free tier: 250 images/day, 10 RPM.
 * API ref: https://ai.google.dev/api/generate-content#v1beta.models.generateContent
 */
class GeminiImageProvider(
    private val apiKey: String,
    private val cacheDir: File,
    private val baseUrl: String = "https://generativelanguage.googleapis.com"
) : ImageGenerationProvider {

    override val name = "Gemini 2.5 Flash Image"
    override val isAvailable = apiKey.isNotBlank()

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    override suspend fun generateEmoji(prompt: String): ImageGenerationResponse = withContext(Dispatchers.IO) {
        try {
            val enhancedPrompt = "Generate an emoji sticker of: $prompt. Style: Apple emoji, high quality, transparent background, centered, single emoji."

            val requestBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", enhancedPrompt)
                            })
                        })
                    })
                })
                put("generationConfig", JSONObject().apply {
                    put("responseModalities", JSONArray().apply {
                        put("TEXT")
                        put("IMAGE")
                    })
                })
            }

            val request = Request.Builder()
                .url("$baseUrl/v1beta/models/gemini-2.0-flash-exp:generateContent")
                .addHeader("Content-Type", "application/json")
                .addHeader("x-goog-api-key", apiKey)
                .post(requestBody.toString().toRequestBody("application/json".toMediaTypeOrNull()))
                .build()

            val response = client.newCall(request).execute()
            response.use { resp ->
                val responseBody = resp.body?.string() ?: ""

                if (!resp.isSuccessful) {
                    return@withContext ImageGenerationResponse.Error(
                        "API error: ${resp.code} - $responseBody",
                        resp.code
                    )
                }

                val json = JSONObject(responseBody)

                // Extract base64 image from response
                val candidates = json.optJSONArray("candidates")
                if (candidates == null || candidates.length() == 0) {
                    return@withContext ImageGenerationResponse.Error("No candidates in response")
                }

                val parts = candidates.getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")

                var base64Data: String? = null
                for (i in 0 until parts.length()) {
                    val part = parts.getJSONObject(i)
                    if (part.has("inlineData")) {
                        base64Data = part.getJSONObject("inlineData").getString("data")
                        break
                    }
                }

                if (base64Data == null) {
                    return@withContext ImageGenerationResponse.Error("No image data in response")
                }

                // Decode and save
                val imageBytes = Base64.decode(base64Data, Base64.DEFAULT)
                val imageFile = File(cacheDir, "emoji_${System.currentTimeMillis()}.png")
                imageFile.writeBytes(imageBytes)

                ImageGenerationResponse.Success(imageFile, provider = name)
            }
        } catch (e: Exception) {
            ImageGenerationResponse.Error(e.message ?: "Unknown error")
        }
    }
}
