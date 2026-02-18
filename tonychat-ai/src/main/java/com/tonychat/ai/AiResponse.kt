package com.tonychat.ai

sealed class AiResponse<out T> {
    data class Success<T>(
        val data: T,
        val fromCache: Boolean = false,
        val provider: String = ""
    ) : AiResponse<T>()

    data class Error<T>(
        val message: String,
        val code: Int = -1
    ) : AiResponse<T>()

    class ConsentRequired<T> : AiResponse<T>()
    class RateLimited<T> : AiResponse<T>()
    class Unavailable<T> : AiResponse<T>()
}
