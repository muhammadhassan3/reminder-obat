package com.muhammhassan.reminderobat.core.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {

    private fun parseDate(pattern: String, date: Date): String {
        val dateFormater = SimpleDateFormat(pattern, Locale("id", "ID"))
        return dateFormater.format(date)
    }
    private fun parseString(pattern: String, date: String): Date {
        val dateFormater = SimpleDateFormat(pattern, Locale("id", "ID"))
        return dateFormater.parse(date) ?: throw IllegalArgumentException("Invalid date argument pattern")
    }

    fun parseDateToString(date: Date): String {
        return parseDate("yyyyMMdd", date)
    }

    fun parseStringToDate(date: String): Date{
        return parseString("yyyyMMdd", date)
    }

    fun parseTimeToString(hour: Int, minute: Int): String {
        val hour = if (hour < 10) StringBuilder("0").append(hour) else hour
        val minute = if (minute < 10) StringBuilder("0").append(minute) else minute
        return "$hour:$minute"
    }

    fun parseDateWithDay(date: Date): String{
        return parseDate("EEEE, dd MMMM yyyy", date)
    }

    fun parseDateWithoutDayName(date: Date): String{
        return parseDate("dd MMMM yyyy", date)
    }

    fun parseStringWithoutDayToDate(date: String): Date{
        return parseString("dd MMMM yyyy", date)
    }

    fun parseDateToISO(date: Date): String{
        return parseDate("yyyy-MM-dd'T'HH:mm:ss.SSSZ", date)
    }
}
