package com.tonychat.community.repository

import android.util.Log
import com.tonychat.community.SupabaseClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.UUID

/**
 * Helper class for uploading images to Supabase Storage
 */
class ImageUploader {
    private val bucketName = "community-images"

    companion object {
        private const val TAG = "ImageUploader"
    }

    /**
     * Upload image to Supabase Storage
     * @param imageFile Image file to upload
     * @return Public URL of uploaded image or null on error
     */
    suspend fun uploadImage(imageFile: File): String? = withContext(Dispatchers.IO) {
        try {
            val fileName = "posts/${UUID.randomUUID()}.jpg"
            val storageUrl = SupabaseClient.getStorageUrl()
            val uploadUrl = "$storageUrl/object/$bucketName/$fileName"

            // Create multipart request
            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "file",
                    imageFile.name,
                    imageFile.asRequestBody("image/jpeg".toMediaType())
                )
                .build()

            val request = Request.Builder()
                .url(uploadUrl)
                .addHeader("apikey", SupabaseClient.getAnonKey())
                .addHeader("Authorization", "Bearer ${SupabaseClient.getAnonKey()}")
                .post(requestBody)
                .build()

            val response = SupabaseClient.getClient().newCall(request).execute()

            if (response.isSuccessful) {
                // Return public URL
                "$storageUrl/object/public/$bucketName/$fileName"
            } else {
                null
            }
        } catch (e: Exception) {
            Log.w(TAG, "Image upload failed", e)
            null
        }
    }
}
