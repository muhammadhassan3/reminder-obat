package com.muhammhassan.reminderobat.ui.view.auth.reset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammhassan.reminderobat.domain.model.ResetTokenModel
import com.muhammhassan.reminderobat.domain.model.UiState
import com.muhammhassan.reminderobat.domain.usecase.UseEmailUseCase
import com.muhammhassan.reminderobat.utils.DialogData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UseEmailViewModel(private val useCase: UseEmailUseCase): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<ResetTokenModel>?>(null)
    val uiState = _uiState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _dialogData = MutableStateFlow(DialogData.init())
    val dialogData = _dialogData.asStateFlow()

    private val _isDialogShow = MutableStateFlow(false)
    val isDialogShow = _isDialogShow.asStateFlow()

    fun setEmail(value: String){
        _email.value = value
    }

    fun setDialogData(value: DialogData){
        _dialogData.value = value
        _isDialogShow.value = true
    }

    fun hideDialog(){
        _isDialogShow.value = false
    }

    fun sendEmail(){
        viewModelScope.launch{
            useCase.getResetToken(email.value).collectLatest {
                _uiState.value = it
            }
        }
    }
}