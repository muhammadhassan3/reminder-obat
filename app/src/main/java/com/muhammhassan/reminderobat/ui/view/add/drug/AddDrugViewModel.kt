package com.muhammhassan.reminderobat.ui.view.add.drug

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.muhammhassan.reminderobat.domain.model.DrugsData

class AddDrugViewModel : ViewModel() {
    val title = mutableStateOf("")
    val weight = mutableStateOf("")
    val type = mutableStateOf("")
    val afterEat = mutableStateOf(0)

    fun setTitle(title: String) {
        this.title.value = title
    }

    fun setWeight(weight: String) {
        this.weight.value = weight
    }

    fun setType(type: String) {
        this.type.value = type
    }

    fun setAfterEat(value: Int){
        this.afterEat.value = value
    }

    fun convertToData(): DrugsData = DrugsData(
        name = title.value,
        weight = weight.value,
        type = type.value,
        afterEat = afterEat.value
    )
}