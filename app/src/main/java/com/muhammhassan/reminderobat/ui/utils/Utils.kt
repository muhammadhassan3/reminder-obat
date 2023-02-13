package com.muhammhassan.reminderobat.ui.utils

import java.text.SimpleDateFormat
import java.util.*

fun parseData(date: Date): String{
    val dateFormater = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id", "ID"))
    return dateFormater.format(date)
}