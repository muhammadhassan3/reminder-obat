package com.muhammhassan.reminderobat.ui.view.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.muhammhassan.reminderobat.domain.model.Articles
import com.muhammhassan.reminderobat.domain.model.DrugItem
import com.muhammhassan.reminderobat.domain.model.GroupedDrugItem
import com.muhammhassan.reminderobat.utils.Utils.parseDate
import java.util.*

class HomeViewModel : ViewModel() {
    val date = mutableStateOf("")
    val data = mutableStateListOf<GroupedDrugItem>()
    val articleData = mutableStateListOf<Articles>()

    init {
        date.value = parseDate(Date())

        data.addAll(listOf(
            GroupedDrugItem(
                id = 0,
                "08:00",
                listOf(DrugItem("Obat 1", " 1 Pill"), DrugItem("Obat 2", " 1 Pill"))
            ), GroupedDrugItem(
                id = 1,
                "08:00",
                listOf(DrugItem("Obat 1", " 1 Pill"), DrugItem("Obat 2", " 1 Pill"))
            ), GroupedDrugItem(
                id = 2,
                "08:00",
                listOf(DrugItem("Obat 1", " 1 Pill"), DrugItem("Obat 2", " 1 Pill"))
            ), GroupedDrugItem(
                id = 3,
                "08:00",
                listOf(DrugItem("Obat 1", " 1 Pill"), DrugItem("Obat 2", " 1 Pill"))
            )
        ))
    }
}