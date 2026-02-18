package com.tonychat.ai

import java.io.File

/**
 * Response wrapper for image editing operations.
 * Parallel to AiResponse but for image-to-image transformations.
 */
sealed class ImageEditResponse {
    data class Success(
        val resultFile: File,
        val fromCache: Boolean = false
    ) : ImageEditResponse()

    data class Error(
        val message: String,
        val code: Int = -1
    ) : ImageEditResponse()

    class ConsentRequired : ImageEditResponse()
    class RateLimited : ImageEditResponse()
}
