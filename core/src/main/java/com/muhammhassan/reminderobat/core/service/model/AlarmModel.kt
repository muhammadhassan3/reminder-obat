package com.muhammhassan.reminderobat.core.service.model

data class AlarmModel(
    val scheduleId: Int,
    val time: String,
    val drugsName: String,
    val days: String,
){

    fun getTitle() = "Sekarang jam $time"
    fun getMessage() = "Sudah waktunya minum obat $drugsName"
}