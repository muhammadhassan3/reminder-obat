package com.muhammhassan.reminderobat.ui.view.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.muhammhassan.remainderobat.domain.model.DrugItem
import com.muhammhassan.remainderobat.domain.model.GroupedDrugItem
import com.muhammhassan.reminderobat.ui.utils.parseData
import kotlinx.coroutines.flow.flowOf
import java.util.*

class HomeViewModel : ViewModel() {
    val date = mutableStateOf("")
    val data = mutableStateListOf<GroupedDrugItem>()

    init {
        date.value = parseData(Date())

        data.addAll(listOf(
            GroupedDrugItem(
                id = 0,
                "08:00",
                listOf(DrugItem("Obat 1", " 1 Pill"), DrugItem("Obat 2", " 1 Pill"))
            ),GroupedDrugItem(
                id = 1,
                "08:00",
                listOf(DrugItem("Obat 1", " 1 Pill"), DrugItem("Obat 2", " 1 Pill"))
            ),GroupedDrugItem(
                id = 2,
                "08:00",
                listOf(DrugItem("Obat 1", " 1 Pill"), DrugItem("Obat 2", " 1 Pill"))
            ),GroupedDrugItem(
                id = 3,
                "08:00",
                listOf(DrugItem("Obat 1", " 1 Pill"), DrugItem("Obat 2", " 1 Pill"))
            )
        ))
    }
}