package com.tonychat.ai.provider

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.tonychat.ai.*
import com.tonychat.ai.security.CertificatePinnerFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File
import java.util.concurrent.TimeUnit

class OpenAiProvider(private val apiKey: String) : AiProvider {
    override val name = "openai"
    override val isOnDevice = false
    override val isAvailable get() = apiKey.isNotBlank()

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .certificatePinner(CertificatePinnerFactory.create())
        .build()
    private val gson = Gson()
    private val jsonMedia = "application/json".toMediaType()

    override suspend fun generateReply(context: List<AiMessage>, count: Int): AiResponse<List<String>> {
        val messages = buildConversation(context)
        messages.add(0, mapOf(
            "role" to "system",
            "content" to "Generate $count brief reply suggestions for the last message. Return ONLY a JSON array of strings, no other text."
        ))
        return executeAndParse(messages) { content ->
            try {
                val arr = JsonParser.parseString(content).asJsonArray
                AiResponse.Success(arr.map { it.asString }, provider = name)
            } catch (e: Exception) {
                AiResponse.Success(content.lines().filter { it.isNotBlank() }.take(count), provider = name)
            }
        }
    }

    override suspend fun summarize(messages: List<AiMessage>, maxLength: Int): AiResponse<String> {
        val conv = buildConversation(messages)
        conv.add(0, mapOf(
            "role" to "system",
            "content" to "Summarize the following chat messages in 3-5 bullet points. Max $maxLength words."
        ))
        return executeAndParse(conv) { AiResponse.Success(it, provider = name) }
    }

    override suspend fun rewriteTone(text: String, tone: ToneStyle): AiResponse<String> {
        val msgs = listOf(
            mapOf("role" to "system", "content" to "Rewrite the following message in a ${tone.name.lowercase()} tone. Return only the rewritten text."),
            mapOf("role" to "user", "content" to text)
        )
        return executeAndParse(msgs) { AiResponse.Success(it, provider = name) }
    }

    override suspend fun translate(text: String, sourceLang: String, targetLang: String): AiResponse<String> {
        val msgs = listOf(
            mapOf("role" to "system", "content" to "Translate from $sourceLang to $targetLang. Return only the translation."),
            mapOf("role" to "user", "content" to text)
        )
        return executeAndParse(msgs) { AiResponse.Success(it, provider = name) }
    }

    suspend fun transcribe(audioFile: File): AiResponse<String> = withContext(Dispatchers.IO) {
        try {
            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "file",
                    audioFile.name,
                    audioFile.asRequestBody("audio/ogg".toMediaTypeOrNull())
                )
                .addFormDataPart("model", "whisper-1")
                .addFormDataPart("response_format", "json")
                .build()

            val request = Request.Builder()
                .url("https://api.openai.com/v1/audio/transcriptions")
                .header("Authorization", "Bearer $apiKey")
                .post(requestBody)
                .build()

            client.newCall(request).execute().use { response ->
                val responseBody = response.body?.string() ?: return@withContext AiResponse.Error("Empty response", response.code)

                when (response.code) {
                    200 -> {
                        val json = JSONObject(responseBody)
                        val transcript = json.getString("text")
                        AiResponse.Success(transcript, provider = name)
                    }
                    413 -> AiResponse.Error("File too large (>25MB)", 413)
                    else -> AiResponse.Error("API error: ${response.code}", response.code)
                }
            }
        } catch (e: Exception) {
            AiResponse.Error(e.message ?: "Unknown error")
        }
    }

    private fun buildConversation(messages: List<AiMessage>): MutableList<Map<String, String>> {
        return messages.map {
            mapOf("role" to if (it.isOutgoing) "user" else "assistant", "content" to it.text)
        }.toMutableList()
    }

    private suspend fun <T> executeAndParse(
        messages: List<Map<String, String>>,
        parse: (String) -> AiResponse<T>
    ): AiResponse<T> = withContext(Dispatchers.IO) {
        try {
            val body = gson.toJson(mapOf(
                "model" to "gpt-4o-mini",
                "messages" to messages,
                "max_tokens" to 512
            ))
            val request = Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer $apiKey")
                .post(body.toRequestBody(jsonMedia))
                .build()

            client.newCall(request).execute().use { response ->
                val responseBody = response.body?.string() ?: return@withContext AiResponse.Error("Empty response", response.code)
                if (!response.isSuccessful) {
                    return@withContext AiResponse.Error("API error: ${response.code}", response.code)
                }
                val json = JsonParser.parseString(responseBody).asJsonObject
                val content = json.getAsJsonArray("choices")
                    .get(0).asJsonObject
                    .getAsJsonObject("message")
                    .get("content").asString.trim()
                parse(content)
            }
        } catch (e: Exception) {
            AiResponse.Error(e.message ?: "Unknown error")
        }
    }
}
