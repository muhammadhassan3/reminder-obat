package com.muhammhassan.reminderobat.core.api

sealed class ApiResponse<out T>{
    object Loading: ApiResponse<Nothing>()
    data class Success<out T>(val data: T): ApiResponse<T>()
    data class Error(val message: String): ApiResponse<Nothing>()
}
