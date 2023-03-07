package com.muhammhassan.reminderobat.ui.view.add.schedule

import androidx.lifecycle.ViewModel
import com.muhammhassan.reminderobat.core.utils.Utils.parseDateWithoutDayName
import com.muhammhassan.reminderobat.core.utils.Utils.parseStringWithoutDayToDate
import com.muhammhassan.reminderobat.domain.model.DrugsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

class AddReminderViewModel: ViewModel() {
    private var data = DrugsData()
    private val _totalReminder = MutableStateFlow(0)
    val totalReminder = _totalReminder.asStateFlow()
    private val hours = mutableListOf<String>()
    private val days = mutableSetOf<Int>()
    private val _startDate = MutableStateFlow(parseDateWithoutDayName(Calendar.getInstance().time))
    val startDate = _startDate.asStateFlow()
    private val _endDate = MutableStateFlow(parseDateWithoutDayName(Calendar.getInstance().time))
    val endDate = _endDate.asStateFlow()


    fun setData(data: DrugsData){
        this.data = data
    }

    fun setStartDate(year: Int, month: Int, day: Int){
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        _startDate.value = parseDateWithoutDayName(calendar.time)
    }

    fun setEndDate(year: Int, month: Int, day: Int){
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        _endDate.value = parseDateWithoutDayName(calendar.time)
    }

    fun increaseReminder(){
        hours.add("08:00")
        _totalReminder.value++
    }

    fun decreaseReminder(){
        hours.removeAt(hours.size-1)
        _totalReminder.value--
    }

    fun setHour(position: Int, value: String){
        hours[position] = value
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
        hour = hours,
        day = days,
        startDate = parseStringWithoutDayToDate(_startDate.value),
        endDate = parseStringWithoutDayToDate(_endDate.value)
    )
}