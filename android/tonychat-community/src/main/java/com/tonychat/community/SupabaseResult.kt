package com.tonychat.community

/**
 * Result type for Supabase operations.
 * Replaces nullable String return type with explicit success/error handling.
 */
sealed class SupabaseResult<out T> {
    data class Success<T>(val data: T) : SupabaseResult<T>()
    data class Error(val code: Int, val message: String, val body: String?) : SupabaseResult<Nothing>()
    data class NetworkError(val exception: Exception) : SupabaseResult<Nothing>()
}
