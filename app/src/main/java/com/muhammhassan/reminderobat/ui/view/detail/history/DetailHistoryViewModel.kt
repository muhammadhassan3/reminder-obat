package com.muhammhassan.reminderobat.ui.view.detail.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammhassan.reminderobat.domain.model.HistoryModel
import com.muhammhassan.reminderobat.domain.usecase.ProgressDetailUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailHistoryViewModel(private val useCase: ProgressDetailUseCase): ViewModel() {
    private val data = MutableStateFlow<HistoryModel?>(null)

    fun getData(id: Long): Flow<HistoryModel?>{
        viewModelScope.launch {
            useCase.getHistory(id).collectLatest {
                data.value = it
            }
        }
        return data
    }
}