package com.muhammhassan.reminderobat.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun parseDate(date: Date): String{
        val dateFormater = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id", "ID"))
        return dateFormater.format(date)
    }

    fun parseStringToDate(date: String): Date{
        val stringFormater = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id", "ID"))
        return stringFormater.parse(date) ?: throw IllegalArgumentException("Invalid date argument pattern")
    }
}