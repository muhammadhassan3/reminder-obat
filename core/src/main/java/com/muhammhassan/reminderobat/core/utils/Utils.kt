package com.muhammhassan.reminderobat.core.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun parseDateToString(date: Date): String{
        val dateFormater = SimpleDateFormat("yyyyMMdd", Locale("id", "ID"))
        return dateFormater.format(date)
    }
}