package com.muhammhassan.reminderobat.ui.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammhassan.reminderobat.core.utils.Utils.parseDateToString
import com.muhammhassan.reminderobat.core.utils.Utils.parseDateWithDay
import com.muhammhassan.reminderobat.domain.model.Articles
import com.muhammhassan.reminderobat.domain.model.GroupedDrugItem
import com.muhammhassan.reminderobat.domain.usecase.HomeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import java.util.Calendar
import java.util.Date

class HomeViewModel(private val useCase: HomeUseCase) : ViewModel() {
    private val _date = MutableStateFlow("")
    val date = _date.asStateFlow()
    private val _data = MutableStateFlow(listOf<GroupedDrugItem>())
    val data = _data.asStateFlow()

    private val _articleData = MutableStateFlow(listOf<Articles>())
    val articleData = _articleData.asStateFlow()

    init {
        _date.value = parseDateWithDay(Date())
    }

    fun getData() {
        val calendar = Calendar.getInstance()
        val hour =
            if (calendar[Calendar.HOUR_OF_DAY] < 10) "0${calendar[Calendar.HOUR_OF_DAY]}" else calendar[Calendar.HOUR_OF_DAY]
        val minute =
            if (calendar[Calendar.MINUTE] < 10) "0${calendar[Calendar.MINUTE]}" else calendar[Calendar.MINUTE]

        val time = "$hour:$minute"
        val todaySchedule =
            useCase.getData(calendar[Calendar.DAY_OF_WEEK], time, parseDateToString(calendar.time))
        //set date for tomorrow
        calendar.add(Calendar.DAY_OF_MONTH, 1)
//        calendar.add(Calendar.DAY_OF_WEEK, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val tomorrowSchedule = useCase.getData(
            calendar[Calendar.DAY_OF_WEEK],
            "00:00",
            parseDateToString(calendar.time)
        )


        combine(todaySchedule, tomorrowSchedule) { today, tomorrow ->
            val tmpData = mutableListOf<GroupedDrugItem>()
            if (today.isNotEmpty()) {
                tmpData.add(GroupedDrugItem(calendar[Calendar.DAY_OF_WEEK], "Hari ini", today))
            }

            if (tomorrow.isNotEmpty()) {
                tmpData.add(
                    GroupedDrugItem(calendar[Calendar.DAY_OF_WEEK] + 1, "Besok", tomorrow)
                )
            }
            _data.value = tmpData
        }.launchIn(viewModelScope)
    }
}