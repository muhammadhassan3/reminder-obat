package com.muhammhassan.reminderobat.core.api.response

data class ResetTokenResponse(
    val token: String,
    val resendToken: String? = null,
    val expire: Long
)