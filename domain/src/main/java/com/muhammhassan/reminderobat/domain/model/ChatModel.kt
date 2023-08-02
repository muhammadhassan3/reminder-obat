package com.muhammhassan.reminderobat.domain.model

import java.text.DecimalFormat
import java.util.Calendar

data class ChatModel(
    var id: String? = null,
    val message: String,
    var timestamp: Long,
    val sender: UserModel,
) {

    fun getTime(): String {
        val date = Calendar.getInstance().also{
            it.timeInMillis = timestamp
        }
        val decFormat = DecimalFormat("00")
        return decFormat.format(date[Calendar.HOUR_OF_DAY]) + ":" + decFormat.format(date[Calendar.MINUTE])
    }

    fun getDate():String{
        val date = Calendar.getInstance().also{
            it.timeInMillis = timestamp
        }
        return String.format("%s/%s/%s", date[Calendar.YEAR],date[Calendar.MONTH], date[Calendar.DAY_OF_MONTH])
    }
}