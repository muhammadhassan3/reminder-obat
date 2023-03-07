package com.muhammhassan.reminderobat.ui.view.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammhassan.reminderobat.domain.model.DrugsModel
import com.muhammhassan.reminderobat.domain.usecase.ReminderDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReminderDetailViewModel(private val useCase: ReminderDetailUseCase): ViewModel() {
    private val _data = MutableStateFlow<DrugsModel?>(null)
    val data = _data.asStateFlow()

    private val _isDialogShow = MutableStateFlow(false)
    val isDialogShow = _isDialogShow.asStateFlow()

    fun hideDialog(){
        _isDialogShow.value = false
    }

    fun showDialog(){
        _isDialogShow.value = true

    }

    fun setData(id: Int){
        viewModelScope.launch {
            useCase.getData(id.toLong()).collect{
                _data.value = it
            }
        }
    }

    fun confirm(drugsId: Long, onProcessFinished: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.reduceStock(drugsId)
            useCase.addHistory(data = data.value!!)
        }
        onProcessFinished.invoke()
    }

    fun reschedule(){
        /*
        Do Reschedule Function
         */
    }
}