package com.tonychat.ai

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Java-friendly bridge for calling AiManager suspend functions from Java code.
 * Each method runs on IO dispatcher and delivers results on the main thread via callback.
 */
object AiManagerBridge {

    fun interface ResultCallback<T> {
        fun onResult(response: AiResponse<T>)
    }

    fun generateReply(context: List<AiMessage>, count: Int, callback: ResultCallback<List<String>>) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                AiManager.generateReply(context, count)
            } catch (e: Exception) {
                AiResponse.Error<List<String>>(e.message ?: "Unknown error")
            }
            kotlinx.coroutines.withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }

    fun summarize(messages: List<AiMessage>, maxLength: Int, callback: ResultCallback<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                AiManager.summarize(messages, maxLength)
            } catch (e: Exception) {
                AiResponse.Error<String>(e.message ?: "Unknown error")
            }
            kotlinx.coroutines.withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }

    fun rewriteTone(text: String, tone: ToneStyle, callback: ResultCallback<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                AiManager.rewriteTone(text, tone)
            } catch (e: Exception) {
                AiResponse.Error<String>(e.message ?: "Unknown error")
            }
            kotlinx.coroutines.withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }

    fun translate(text: String, sourceLang: String, targetLang: String, callback: ResultCallback<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                AiManager.translate(text, sourceLang, targetLang)
            } catch (e: Exception) {
                AiResponse.Error<String>(e.message ?: "Unknown error")
            }
            kotlinx.coroutines.withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }
}
