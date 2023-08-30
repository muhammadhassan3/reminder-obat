package com.muhammhassan.reminderobat.core.api.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val email: String,
    @SerializedName("phoneNumber")
    val phone: String
)
