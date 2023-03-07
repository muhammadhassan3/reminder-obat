package com.muhammhassan.reminderobat.domain.model

data class DrugsModel(
    val id: Long,
    val scheduleId: Long,
    val title: String,
    val time: String,
    val weight: String,
    val type: String,
    val afterEat: Int,
    val startDate: String,
    val endDate: String,
    val condition: String,
    val stock: Int,
)
