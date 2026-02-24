package com.tonychat.ai

import java.io.File

/**
 * Provider interface for image editing operations (image → image transformation).
 * Separate from AiProvider which handles text-based operations.
 */
interface ImageEditProvider {
    val name: String
    val isAvailable: Boolean

    /**
     * Remove background from the given image file.
     * @param imageFile Input image file
     * @return ImageEditResponse with result file path or error
     */
    suspend fun removeBackground(imageFile: File): ImageEditResponse
}
