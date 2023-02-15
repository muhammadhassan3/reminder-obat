package com.muhammhassan.reminderobat.ui.view.add.schedule

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.muhammhassan.reminderobat.domain.model.DrugsData
import com.muhammhassan.reminderobat.utils.Utils.parseDate
import com.muhammhassan.reminderobat.utils.Utils.parseStringToDate
import java.util.*

class AddReminderViewModel: ViewModel() {
    private var data = DrugsData()
    val totalReminder = mutableStateOf(0)
    val hours = mutableListOf<String>()
    val days = mutableSetOf<Int>()
    val startDate = mutableStateOf("")
    val endDate = mutableStateOf("")

    init {

    }

    fun setData(data: DrugsData){
        this.data = data
    }

    fun setStartDate(year: Int, month: Int, day: Int){
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        startDate.value = parseDate(calendar.time)
    }

    fun setEndDate(year: Int, month: Int, day: Int){
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        endDate.value = parseDate(calendar.time)
    }

    fun increaseReminder(){
        hours.add("08:00")
        totalReminder.value++
    }

    fun decreaseReminder(){
        hours.removeAt(hours.size-1)
        totalReminder.value--
    }

    fun setHour(position: Int, value: String?){
        if(value != null) {
            hours[position] = value
        }else hours.removeAt(position)
    }

    fun setDay(day: Int, selected: Boolean){
        if(selected) {
            days.add(day)
        }else days.remove(day)
    }

    fun convertToData() = DrugsData(
        name = data.name,
        weight = data.weight,
        type = data.type,
        afterEat = data.afterEat,
        hour = hours.subList(0, totalReminder.value - 1),
        day = days,
        startDate = parseStringToDate(startDate.value),
        endDate = parseStringToDate(endDate.value)
    )
}