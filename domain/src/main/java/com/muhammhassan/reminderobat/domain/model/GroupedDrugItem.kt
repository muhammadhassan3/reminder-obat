package com.muhammhassan.reminderobat.domain.model

data class GroupedDrugItem(
    val id: Int,
    val title: String,
    val item: List<DrugItem>
)