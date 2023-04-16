package com.muhammhassan.reminderobat.domain.model

data class HistoryModel(
    val drugName: String,
    val drugsWeight: String,
    val drugsType: String,
    val time: String,
    val afterEat: Int,
    val confirmTime: String,
    val condition: String,
    val stock: Int,
    val day: Int,
    val status: String,
)