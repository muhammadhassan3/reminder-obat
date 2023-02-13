package com.muhammhassan.reminderobat.ui.view.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.muhammhassan.reminderobat.ui.utils.parseData
import java.util.Date

class HomeViewModel: ViewModel() {
    val date = mutableStateOf("")
    init {
        date.value = parseData(Date())
    }
}