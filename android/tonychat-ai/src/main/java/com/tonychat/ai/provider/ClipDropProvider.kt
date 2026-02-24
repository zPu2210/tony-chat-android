package com.tonychat.ai.provider

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.tonychat.ai.BuildConfig
import com.tonychat.ai.ImageEditResponse
import com.tonychat.ai.ImageGenerationResponse
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
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

/**
 * Image tool provider routing through Supabase Edge Functions:
 * - Remove Background  → POST /ai-remove-bg     (multipart)
 * - Upscale            → POST /ai-upscale        (multipart)
 * - Remove Text        → POST /ai-remove-text    (multipart)
 * - Generate Image     → POST /ai-generate-image (JSON)
 *
 * Auth: X-Device-Id header + Supabase anon key Bearer token.
 * Rate limits enforced server-side; remaining quota in X-Remaining header.
 */
class ClipDropProvider(
    private val deviceId: String,
    private val cacheDir: File
) {
    companion object {
        private const val BASE_URL = BuildConfig.SUPABASE_URL + "/functions/v1"
        private const val MAX_IMAGE_PIXELS = 16_000_000 // 16MP
        private const val JPEG_QUALITY = 95
    }

    val isAvailable: Boolean get() = deviceId.isNotBlank()

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun removeBackground(imageFile: File): ImageEditResponse = withContext(Dispatchers.IO) {
        if (!imageFile.exists()) return@withContext ImageEditResponse.Error("Image file not found")

        try {
            val prepared = prepareImage(imageFile, 25_000_000)
            val body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image_file", prepared.name,
                    prepared.asRequestBody("image/png".toMediaTypeOrNull()))
                .build()

            val request = Request.Builder()
                .url("$BASE_URL/ai-remove-bg")
                .header("X-Device-Id", deviceId)
                .header("Authorization", "Bearer ${BuildConfig.SUPABASE_ANON_KEY}")
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
                .build()

            val request = Request.Builder()
                .url("$BASE_URL/ai-upscale")
                .header("X-Device-Id", deviceId)
                .header("Authorization", "Bearer ${BuildConfig.SUPABASE_ANON_KEY}")
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
                .url("$BASE_URL/ai-remove-text")
                .header("X-Device-Id", deviceId)
                .header("Authorization", "Bearer ${BuildConfig.SUPABASE_ANON_KEY}")
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
            val json = JSONObject().apply { put("prompt", prompt.take(1000)) }
            val requestBody = json.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaType())

            val request = Request.Builder()
                .url("$BASE_URL/ai-generate-image")
                .header("X-Device-Id", deviceId)
                .header("Authorization", "Bearer ${BuildConfig.SUPABASE_ANON_KEY}")
                .post(requestBody)
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
                        ImageGenerationResponse.Success(resultFile, provider = "edge-function")
                    }
                    429 -> ImageGenerationResponse.Error("Daily limit reached. Try again tomorrow.")
                    400 -> ImageGenerationResponse.Error("Invalid prompt: ${resp.body?.string() ?: "bad request"}")
                    else -> ImageGenerationResponse.Error("Server error (${resp.code})")
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
                429 -> ImageEditResponse.RateLimited()
                400 -> ImageEditResponse.Error("Invalid image: ${resp.body?.string() ?: "bad request"}", 400)
                else -> ImageEditResponse.Error("Server error (${resp.code})", resp.code)
            }
        }
    }

    /** Resize image if it exceeds pixel limit to prevent API rejection. */
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
