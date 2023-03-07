package com.muhammhassan.reminderobat.ui.view.add.drug

import androidx.lifecycle.ViewModel
import com.muhammhassan.reminderobat.domain.model.DrugsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddDrugViewModel : ViewModel() {
    private val _title = MutableStateFlow<String?>(null)
    val title = _title.asStateFlow()
    private val _weight = MutableStateFlow<String?>(null)
    val weight = _weight.asStateFlow()
    private val _type = MutableStateFlow<String?>(null)
    val type = _type.asStateFlow()
    private val _afterEat = MutableStateFlow(0)
    val afterEat = _afterEat.asStateFlow()

    fun setTitle(title: String) {
        this._title.value = title
    }

    fun setWeight(weight: String) {
        this._weight.value = weight
    }

    fun setType(type: String) {
        this._type.value = type
    }

    fun setAfterEat(value: Int){
        this._afterEat.value = value
    }

    fun convertToData(): DrugsData = DrugsData(
        name = _title.value,
        weight = _weight.value,
        type = _type.value,
        afterEat = _afterEat.value
    )
}