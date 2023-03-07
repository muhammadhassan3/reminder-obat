package com.muhammhassan.reminderobat.ui.view.add.stock

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammhassan.reminderobat.core.service.alarm.AndroidAlarmScheduler
import com.muhammhassan.reminderobat.domain.model.DrugsData
import com.muhammhassan.reminderobat.domain.usecase.AddStockUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class AddStockViewModel(private val useCase: AddStockUseCase): ViewModel() {
    private var data = DrugsData()
    private val _condition = MutableStateFlow<String?>(null)
    val condition = _condition.asStateFlow()

    private val _stock = MutableStateFlow(0)
    val stock = _stock.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog = _showDialog.asStateFlow()

    fun hideDialog(){
        _showDialog.value = false
    }

    fun setCondition(value: String){
        _condition.value = value
    }

    fun setStock(value: String){
        try{
            if(stock.value == 0 && value.endsWith("0")){
                _stock.value = value.toInt()/10
            }else if(value == ""){
                _stock.value = 0
            }else{
                _stock.value = value.toInt()
            }
        }catch (_: Exception){
        }
    }

    fun setData(data: DrugsData){
        this.data = data
    }

    fun save(context: Context, onAfterDataSaved: () -> Unit){
        val scheduleHelper = AndroidAlarmScheduler(context = context)
        val finishedData = DrugsData(
            name = data.name,
            hour = data.hour,
            type = data.type,
            weight = data.weight,
            stock = stock.value,
            afterEat = data.afterEat,
            condition = condition.value,
            day = data.day,
            endDate = data.endDate,
            startDate = data.startDate
        )

        if(finishedData.isPropertiesValid()){
            viewModelScope.launch(Dispatchers.IO){
                useCase.addData(finishedData).collect{ savedData ->
                    if(finishedData.startDate!!.before(Calendar.getInstance().time)){ // Check if start date is before today
                        savedData.scheduleList.forEach { model ->
                            scheduleHelper.scheduleReminder(model)
                            Timber.i("Schedule added with id ${model.scheduleId}")
                        }
                    }else{
                        scheduleHelper.scheduleStartReminder(savedData.drugsId.toInt(), finishedData.startDate!!)
                    }

                    scheduleHelper.scheduleClearReminder(savedData.drugsId.toInt(), finishedData.endDate!!)
                }
                Timber.i("Data saved")
            }

            onAfterDataSaved.invoke()
        }else{
            _showDialog.value = true
        }
    }
}