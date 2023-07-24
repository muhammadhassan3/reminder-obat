package com.muhammhassan.reminderobat.ui.view.education

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammhassan.reminderobat.domain.model.Articles
import com.muhammhassan.reminderobat.domain.model.UiState
import com.muhammhassan.reminderobat.domain.usecase.EducationUseCase
import com.muhammhassan.reminderobat.ui.component.ButtonType
import com.muhammhassan.reminderobat.utils.DialogData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EducationViewModel(private val useCase: EducationUseCase) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Articles>>> = MutableStateFlow(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _dialogData = MutableStateFlow(DialogData.init())
    val dialogData = _dialogData.asStateFlow()

    private val _isDialogShow = MutableStateFlow(false)
    val isDialogShow = _isDialogShow.asStateFlow()

    init {
        getData()
    }

    fun setError(message: String, action: () -> Unit){
        _dialogData.value = DialogData(
            title = "Pemberitahuan",
            message = message,
            buttonType = ButtonType.NEUTRAL,
            onNeutralAction = {
                _isDialogShow.value = false
                action.invoke()
            }
        )
        _isDialogShow.value = true
    }

    private fun getData(){
        viewModelScope.launch {
            useCase.getData().collect{
                _uiState.value = it
            }
        }
    }
}