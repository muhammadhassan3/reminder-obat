package com.muhammhassan.reminderobat.core.utils

import okhttp3.ResponseBody
import okio.IOException
import org.json.JSONObject
import retrofit2.Response
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

    fun parseDateFromISO(date: String): Date{
        return parseString("yyyy-MM-dd'T'HH:mm:ss.SSSZ", date)
    }

    fun parseDateWithTime(date: Date): String{
        return parseDate("dd MMMM yyyy HH:mm ", date)
    }
    fun parseDayName(day: Int): String = when (day) {
        Calendar.MONDAY -> "Senin"
        Calendar.TUESDAY -> "Selasa"
        Calendar.WEDNESDAY -> "Rabu"
        Calendar.THURSDAY -> "Kamis"
        Calendar.FRIDAY -> "Jum'at"
        Calendar.SATURDAY -> "Sabtu"
        Calendar.SUNDAY -> "Minggu"
        else -> "Tidak terdefinisi"
    }

    fun <T> Response<T>.parseError(key: String = "message"): String{
        return try{
            this.errorBody()?.string().runCatching {
                this?.let{
                    JSONObject(it).getString(key)
                }
            }.getOrNull() ?: this.message()
        }catch (e: IOException){
            e.message.toString()
        }
    }
}
