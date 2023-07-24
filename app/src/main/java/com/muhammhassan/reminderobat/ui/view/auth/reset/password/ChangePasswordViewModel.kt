package com.muhammhassan.reminderobat.ui.view.auth.reset.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammhassan.reminderobat.domain.model.UiState
import com.muhammhassan.reminderobat.domain.usecase.ChangePasswordUseCase
import com.muhammhassan.reminderobat.ui.component.ButtonType
import com.muhammhassan.reminderobat.utils.DialogData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChangePasswordViewModel(private val useCase: ChangePasswordUseCase): ViewModel() {
    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    private val _state = MutableStateFlow<UiState<String>?>(null)
    val state = _state.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    private val _isDialogShow = MutableStateFlow(false)
    val isDialogShow = _isDialogShow.asStateFlow()

    private val _dialogData = MutableStateFlow(DialogData.init())
    val dialogData = _dialogData.asStateFlow()

    fun hideDialog(){
        _isDialogShow.value = false
    }

    fun setToken(value: String){
        _token.value = value
    }

    fun setPassword(value: String){
        _password.value = value
    }

    fun setConfirmPassword(value: String){
        _confirmPassword.value = value
    }

    fun setDialogData(value: DialogData){
        _dialogData.value = value
        _isDialogShow.value = true
    }

    fun save(){
        if(password.value.trim() != confirmPassword.value.trim()){
            _dialogData.value = DialogData(
                title = "Pemberitahuan",
                message = "Pastikan kembali konfirmasi password yang kamu tuliskan sama dengan kolom password",
                buttonType = ButtonType.NEUTRAL,
                onNeutralAction = { hideDialog() }
            )
            _isDialogShow.value = true
            return
        }

        viewModelScope.launch {
            useCase.changePassword(token.value, password.value).collect{
                _state.value = it
            }
        }
    }
}