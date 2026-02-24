package com.tonychat.ai.provider

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.tonychat.ai.ImageEditResponse
import com.tonychat.ai.ImageGenerationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

/**
 * Unified ClipDrop API provider for 4 image operations:
 * - Remove Background
 * - Upscale (2x)
 * - Remove Text
 * - Text-to-Image generation
 *
 * API docs: https://clipdrop.co/apis/docs
 */
class ClipDropProvider(
    private val apiKey: String,
    private val cacheDir: File
) {
    companion object {
        private const val BASE_URL = "https://clipdrop-api.co"
        private const val MAX_IMAGE_PIXELS = 16_000_000 // 16MP
        private const val JPEG_QUALITY = 95
    }

    val isAvailable: Boolean get() = apiKey.isNotBlank()

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun removeBackground(imageFile: File): ImageEditResponse = withContext(Dispatchers.IO) {
        if (!imageFile.exists()) return@withContext ImageEditResponse.Error("Image file not found")

        try {
            val prepared = prepareImage(imageFile, 25_000_000) // 25MP limit for remove-bg
            val body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image_file", prepared.name,
                    prepared.asRequestBody("image/png".toMediaTypeOrNull()))
                .build()

            val request = Request.Builder()
                .url("$BASE_URL/remove-background/v1")
                .header("x-api-key", apiKey)
                .post(body)
                .build()

            executeImageRequest(request, "removebg")
        } catch (e: Exception) {
            ImageEditResponse.Error(e.message ?: "Network error")
        }
    }

    suspend fun upscale(imageFile: File): ImageEditResponse = withContext(Dispatchers.IO) {
        if (!imageFile.exists()) return@withContext ImageEditResponse.Error("Image file not found")

        try {
            val prepared = prepareImage(imageFile, MAX_IMAGE_PIXELS)
            val body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image_file", prepared.name,
                    prepared.asRequestBody("image/png".toMediaTypeOrNull()))
                .addFormDataPart("target_width", "4096")
                .addFormDataPart("target_height", "4096")
                .build()

            val request = Request.Builder()
                .url("$BASE_URL/image-upscaling/v1/upscale")
                .header("x-api-key", apiKey)
                .post(body)
                .build()

            executeImageRequest(request, "upscale")
        } catch (e: Exception) {
            ImageEditResponse.Error(e.message ?: "Network error")
        }
    }

    suspend fun removeText(imageFile: File): ImageEditResponse = withContext(Dispatchers.IO) {
        if (!imageFile.exists()) return@withContext ImageEditResponse.Error("Image file not found")

        try {
            val prepared = prepareImage(imageFile, MAX_IMAGE_PIXELS)
            val body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image_file", prepared.name,
                    prepared.asRequestBody("image/png".toMediaTypeOrNull()))
                .build()

            val request = Request.Builder()
                .url("$BASE_URL/remove-text/v1")
                .header("x-api-key", apiKey)
                .post(body)
                .build()

            executeImageRequest(request, "removetext")
        } catch (e: Exception) {
            ImageEditResponse.Error(e.message ?: "Network error")
        }
    }

    suspend fun textToImage(prompt: String): ImageGenerationResponse = withContext(Dispatchers.IO) {
        if (prompt.isBlank()) return@withContext ImageGenerationResponse.Error("Prompt cannot be empty")

        try {
            val body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("prompt", prompt.take(1000))
                .build()

            val request = Request.Builder()
                .url("$BASE_URL/text-to-image/v1")
                .header("x-api-key", apiKey)
                .post(body)
                .build()

            val response = client.newCall(request).execute()
            response.use { resp ->
                when (resp.code) {
                    200 -> {
                        val resultFile = File(cacheDir, "generated_${System.currentTimeMillis()}.png")
                        cacheDir.mkdirs()
                        resp.body?.byteStream()?.use { input ->
                            resultFile.outputStream().use { output -> input.copyTo(output) }
                        } ?: return@withContext ImageGenerationResponse.Error("Empty response")
                        ImageGenerationResponse.Success(resultFile, provider = "clipdrop")
                    }
                    402 -> ImageGenerationResponse.Error("Image processing temporarily unavailable")
                    429 -> ImageGenerationResponse.Error("Too many requests, try again in a minute")
                    400 -> ImageGenerationResponse.Error("Invalid prompt: ${resp.body?.string() ?: "bad request"}")
                    else -> ImageGenerationResponse.Error("API error (${resp.code})")
                }
            }
        } catch (e: Exception) {
            ImageGenerationResponse.Error(e.message ?: "Network error")
        }
    }

    private fun executeImageRequest(request: Request, prefix: String): ImageEditResponse {
        val response = client.newCall(request).execute()
        return response.use { resp ->
            when (resp.code) {
                200 -> {
                    val resultFile = File(cacheDir, "${prefix}_${System.currentTimeMillis()}.png")
                    cacheDir.mkdirs()
                    resp.body?.byteStream()?.use { input ->
                        resultFile.outputStream().use { output -> input.copyTo(output) }
                    } ?: return ImageEditResponse.Error("Empty response")
                    ImageEditResponse.Success(resultFile)
                }
                402 -> ImageEditResponse.Error("Image processing temporarily unavailable", 402)
                429 -> ImageEditResponse.RateLimited()
                400 -> ImageEditResponse.Error("Invalid image: ${resp.body?.string() ?: "bad request"}", 400)
                else -> ImageEditResponse.Error("API error (${resp.code})", resp.code)
            }
        }
    }

    /**
     * Resize image if it exceeds pixel limit to prevent API rejection.
     * Returns original file if within limits, otherwise a compressed temp file.
     */
    private fun prepareImage(imageFile: File, maxPixels: Int): File {
        val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeFile(imageFile.absolutePath, options)
        val pixels = options.outWidth.toLong() * options.outHeight.toLong()

        if (pixels <= maxPixels) return imageFile

        val scale = Math.sqrt(maxPixels.toDouble() / pixels).toFloat()
        val newW = (options.outWidth * scale).toInt()
        val newH = (options.outHeight * scale).toInt()

        val sampleSize = calculateSampleSize(options.outWidth, options.outHeight, newW, newH)
        val decodeOpts = BitmapFactory.Options().apply { inSampleSize = sampleSize }
        val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath, decodeOpts)
            ?: return imageFile

        val scaled = Bitmap.createScaledBitmap(bitmap, newW, newH, true)
        if (scaled !== bitmap) bitmap.recycle()

        val tempFile = File(cacheDir, "prep_${System.currentTimeMillis()}.jpg")
        cacheDir.mkdirs()
        FileOutputStream(tempFile).use { out ->
            scaled.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, out)
        }
        scaled.recycle()
        return tempFile
    }

    private fun calculateSampleSize(srcW: Int, srcH: Int, reqW: Int, reqH: Int): Int {
        var sampleSize = 1
        if (srcH > reqH || srcW > reqW) {
            val halfH = srcH / 2
            val halfW = srcW / 2
            while (halfH / sampleSize >= reqH && halfW / sampleSize >= reqW) {
                sampleSize *= 2
            }
        }
        return sampleSize
    }
}
