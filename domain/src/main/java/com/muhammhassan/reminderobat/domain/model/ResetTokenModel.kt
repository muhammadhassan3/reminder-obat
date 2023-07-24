package com.muhammhassan.reminderobat.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResetTokenModel(
    val verifyToken: String,
    val refreshToken: String?,
    val expires: Long
): Parcelable