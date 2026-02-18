package com.tonychat.ai

import java.io.File

/**
 * Response wrapper for text-to-image generation operations.
 * Parallel to ImageEditResponse but for prompt→image generation.
 */
sealed class ImageGenerationResponse {
    data class Success(
        val imageFile: File,
        val fromCache: Boolean = false,
        val provider: String = ""
    ) : ImageGenerationResponse()

    data class Error(
        val message: String,
        val code: Int = -1
    ) : ImageGenerationResponse()

    class ConsentRequired : ImageGenerationResponse()
    class RateLimited : ImageGenerationResponse()
}
