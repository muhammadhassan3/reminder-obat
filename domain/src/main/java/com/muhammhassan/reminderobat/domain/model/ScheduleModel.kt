package com.muhammhassan.reminderobat.domain.model

data class ScheduleModel(
    val name: String,
    val type: String,
    val weight: String,
    val time: String,
    val afterEat: Int,
    val condition: String
)
