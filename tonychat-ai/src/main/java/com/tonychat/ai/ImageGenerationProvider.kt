package com.tonychat.ai

/**
 * Provider interface for text-to-image generation (emoji creation).
 * Separate from ImageEditProvider (image→image) and AiProvider (text→text).
 */
interface ImageGenerationProvider {
    val name: String
    val isAvailable: Boolean

    /**
     * Generate an emoji image from text prompt.
     * @param prompt User description of desired emoji
     * @return ImageGenerationResponse with generated image file or error
     */
    suspend fun generateEmoji(prompt: String): ImageGenerationResponse
}
