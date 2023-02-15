package com.muhammhassan.reminderobat.domain.model

import com.muhammhassan.reminderobat.domain.model.DrugItem

data class GroupedDrugItem(
    val id: Int,
    val title: String,
    val item: List<DrugItem>
)