package com.muhammhassan.reminderobat.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*
@Parcelize
data class DrugsData(
    val name: String? = null,
    val weight: String? = null,
    val type: String? = null,
    val hour: List<String>? = null,
    val day: Set<Int>? = null,
    val afterEat: Int = 0,
    val startDate: Date? = null,
    val endDate: Date? = null,
    val condition: String? = null,
    val stock: Int = 0
): Parcelable
