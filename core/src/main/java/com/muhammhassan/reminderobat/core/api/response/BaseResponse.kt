package com.muhammhassan.reminderobat.core.api.response

data class BaseResponse<T>(
    val error: Boolean,
    val message: String? = null,
    val data: T
)
