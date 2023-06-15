package com.muhammhassan.reminderobat.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Articles(
    val id: Long,
    val title: String,
    val content: String,
    val image: String,
): Parcelable