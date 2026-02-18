package com.tonychat.ai.provider

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.tonychat.ai.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

class AnthropicProvider(private val apiKey: String) : AiProvider {
    override val name = "anthropic"
    override val isOnDevice = false
    override val isAvailable get() = apiKey.isNotBlank()

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()
    private val jsonMedia = "application/json".toMediaType()

    override suspend fun generateReply(context: List<AiMessage>, count: Int): AiResponse<List<String>> {
        return execute(
            system = "Generate $count brief reply suggestions for the last message. Return ONLY a JSON array of strings.",
            messages = buildConversation(context)
        ) { content ->
            try {
                val arr = JsonParser.parseString(content).asJsonArray
                AiResponse.Success(arr.map { it.asString }, provider = name)
            } catch (e: Exception) {
                AiResponse.Success(content.lines().filter { it.isNotBlank() }.take(count), provider = name)
            }
        }
    }

    override suspend fun summarize(messages: List<AiMessage>, maxLength: Int): AiResponse<String> {
        return execute(
            system = "Summarize the following chat messages in 3-5 bullet points. Max $maxLength words.",
            messages = buildConversation(messages)
        ) { AiResponse.Success(it, provider = name) }
    }

    override suspend fun rewriteTone(text: String, tone: ToneStyle): AiResponse<String> {
        return execute(
            system = "Rewrite the following message in a ${tone.name.lowercase()} tone. Return only the rewritten text.",
            messages = listOf(mapOf("role" to "user", "content" to text))
        ) { AiResponse.Success(it, provider = name) }
    }

    override suspend fun translate(text: String, sourceLang: String, targetLang: String): AiResponse<String> {
        return execute(
            system = "Translate from $sourceLang to $targetLang. Return only the translation.",
            messages = listOf(mapOf("role" to "user", "content" to text))
        ) { AiResponse.Success(it, provider = name) }
    }

    private fun buildConversation(messages: List<AiMessage>): List<Map<String, String>> {
        return messages.map {
            mapOf("role" to if (it.isOutgoing) "user" else "assistant", "content" to it.text)
        }
    }

    private suspend fun <T> execute(
        system: String,
        messages: List<Map<String, String>>,
        parse: (String) -> AiResponse<T>
    ): AiResponse<T> = withContext(Dispatchers.IO) {
        try {
            val body = gson.toJson(mapOf(
                "model" to "claude-3-haiku-20240307",
                "max_tokens" to 512,
                "system" to system,
                "messages" to messages
            ))
            val request = Request.Builder()
                .url("https://api.anthropic.com/v1/messages")
                .header("x-api-key", apiKey)
                .header("anthropic-version", "2023-06-01")
                .header("content-type", "application/json")
                .post(body.toRequestBody(jsonMedia))
                .build()
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: return@withContext AiResponse.Error("Empty response", response.code)
            if (!response.isSuccessful) {
                return@withContext AiResponse.Error("API error: ${response.code}", response.code)
            }
            val json = JsonParser.parseString(responseBody).asJsonObject
            val content = json.getAsJsonArray("content")
                .get(0).asJsonObject
                .get("text").asString.trim()
            parse(content)
        } catch (e: Exception) {
            AiResponse.Error(e.message ?: "Unknown error")
        }
    }
}
