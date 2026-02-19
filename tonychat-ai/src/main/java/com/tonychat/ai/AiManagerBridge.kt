package com.tonychat.ai

import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Java-friendly bridge for calling AiManager suspend functions from Java code.
 * Each method runs on IO dispatcher and delivers results on the main thread via callback.
 */
object AiManagerBridge {
    private const val TAG = "AiManagerBridge"

    private val scope: CoroutineScope
        get() = ProcessLifecycleOwner.get().lifecycleScope

    fun interface ResultCallback<T> {
        fun onResult(response: AiResponse<T>)
    }

    fun interface ImageEditCallback {
        fun onResult(response: ImageEditResponse)
    }

    fun interface ImageGenerationCallback {
        fun onResult(response: ImageGenerationResponse)
    }

    fun generateReply(context: List<AiMessage>, count: Int, callback: ResultCallback<List<String>>) {
        scope.launch(Dispatchers.IO) {
            val result = try {
                AiManager.generateReply(context, count)
            } catch (e: Exception) {
                Log.w(TAG, "generateReply failed", e)
                AiResponse.Error<List<String>>(e.message ?: "Unknown error")
            }
            withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }

    fun summarize(messages: List<AiMessage>, maxLength: Int, callback: ResultCallback<String>) {
        scope.launch(Dispatchers.IO) {
            val result = try {
                AiManager.summarize(messages, maxLength)
            } catch (e: Exception) {
                Log.w(TAG, "summarize failed", e)
                AiResponse.Error<String>(e.message ?: "Unknown error")
            }
            withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }

    fun rewriteTone(text: String, tone: ToneStyle, callback: ResultCallback<String>) {
        scope.launch(Dispatchers.IO) {
            val result = try {
                AiManager.rewriteTone(text, tone)
            } catch (e: Exception) {
                Log.w(TAG, "rewriteTone failed", e)
                AiResponse.Error<String>(e.message ?: "Unknown error")
            }
            withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }

    fun translate(text: String, sourceLang: String, targetLang: String, callback: ResultCallback<String>) {
        scope.launch(Dispatchers.IO) {
            val result = try {
                AiManager.translate(text, sourceLang, targetLang)
            } catch (e: Exception) {
                Log.w(TAG, "translate failed", e)
                AiResponse.Error<String>(e.message ?: "Unknown error")
            }
            withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }

    fun removeBackground(imageFile: File, callback: ImageEditCallback) {
        scope.launch(Dispatchers.IO) {
            val result = try {
                AiManager.removeBackground(imageFile)
            } catch (e: Exception) {
                Log.w(TAG, "removeBackground failed", e)
                ImageEditResponse.Error(e.message ?: "Unknown error")
            }
            withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }

    fun generateEmoji(prompt: String, callback: ImageGenerationCallback) {
        scope.launch(Dispatchers.IO) {
            val result = try {
                AiManager.generateEmoji(prompt)
            } catch (e: Exception) {
                Log.w(TAG, "generateEmoji failed", e)
                ImageGenerationResponse.Error(e.message ?: "Unknown error")
            }
            withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }

    fun transcribeVoice(audioFile: File, callback: ResultCallback<String>) {
        scope.launch(Dispatchers.IO) {
            val result = try {
                AiManager.transcribeVoice(audioFile)
            } catch (e: Exception) {
                Log.w(TAG, "transcribeVoice failed", e)
                AiResponse.Error<String>(e.message ?: "Unknown error")
            }
            withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }
}
