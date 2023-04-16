package com.muhammhassan.reminderobat.ui.view.detail.schedule

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammhassan.reminderobat.core.service.alarm.AndroidAlarmScheduler
import com.muhammhassan.reminderobat.domain.model.ScheduleModel
import com.muhammhassan.reminderobat.domain.usecase.ScheduleDetailUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailScheduleViewModel(private val useCase: ScheduleDetailUseCase): ViewModel() {
    private val data = MutableStateFlow<ScheduleModel?>(null)

    fun getSchedule(id: Long): Flow<ScheduleModel?>{
        viewModelScope.launch {
            useCase.getDetail(id).collectLatest {
                data.value = it
            }
        }
        return data
    }

    fun deleteSchedule(id: Long, context: Context){
        val scheduler = AndroidAlarmScheduler(context)
        useCase.delete(id)
        scheduler.cancelSchedule(id.toInt())
    }
}