package com.tonychat.ai.provider

import com.tonychat.ai.ImageEditProvider
import com.tonychat.ai.ImageEditResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Implementation of background removal using remove.bg API.
 * API docs: https://www.remove.bg/api
 */
class RemoveBgProvider(
    private val apiKey: String,
    private val cacheDir: File,
    private val baseUrl: String = "https://api.remove.bg"
) : ImageEditProvider {

    override val name = "remove.bg"
    override val isAvailable = apiKey.isNotBlank()

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    override suspend fun removeBackground(imageFile: File): ImageEditResponse = withContext(Dispatchers.IO) {
        if (!imageFile.exists()) {
            return@withContext ImageEditResponse.Error("Image file not found")
        }

        try {
            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "image_file",
                    imageFile.name,
                    imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                )
                .addFormDataPart("size", "auto")
                .build()

            val request = Request.Builder()
                .url("$baseUrl/v1.0/removebg")
                .header("X-Api-Key", apiKey)
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            response.use { resp ->
                when (resp.code) {
                    200 -> {
                        val resultFile = File(cacheDir, "edited_${System.currentTimeMillis()}.png")
                        cacheDir.mkdirs()

                        resp.body?.byteStream()?.use { input ->
                            resultFile.outputStream().use { output ->
                                input.copyTo(output)
                            }
                        } ?: return@withContext ImageEditResponse.Error("Empty response body")

                        ImageEditResponse.Success(resultFile)
                    }
                    402 -> ImageEditResponse.Error("Insufficient API credits", 402)
                    429 -> ImageEditResponse.RateLimited()
                    403 -> ImageEditResponse.Error("Invalid API key", 403)
                    400 -> {
                        val errorMsg = resp.body?.string() ?: "Bad request"
                        ImageEditResponse.Error("Invalid image: $errorMsg", 400)
                    }
                    else -> {
                        val errorMsg = resp.body?.string() ?: "Unknown error"
                        ImageEditResponse.Error("API error (${resp.code}): $errorMsg", resp.code)
                    }
                }
            }
        } catch (e: Exception) {
            ImageEditResponse.Error(e.message ?: "Network error")
        }
    }
}
