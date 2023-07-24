package com.muhammhassan.reminderobat.utils

import java.text.DecimalFormat

object Utils {
    fun getTimeFormatFromInt(timeInSecond: Int): String{
        val decimalFormat = DecimalFormat("00")
        val minute = timeInSecond / 60
        val seconds = timeInSecond % 60

        return decimalFormat.format(minute)+":"+decimalFormat.format(seconds)
    }
}