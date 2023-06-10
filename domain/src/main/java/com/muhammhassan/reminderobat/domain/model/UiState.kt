package com.muhammhassan.reminderobat.domain.model

sealed class UiState<out T>{
    object Loading: UiState<Nothing>()
    data class Success<out T>(val data: T): UiState<T>()
    data class Error<out T>(val errorMessage: String): UiState<T>()
}
