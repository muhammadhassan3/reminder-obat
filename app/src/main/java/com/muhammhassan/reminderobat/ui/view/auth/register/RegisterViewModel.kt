package com.muhammhassan.reminderobat.ui.view.auth.register

import androidx.core.util.PatternsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammhassan.reminderobat.domain.model.UiState
import com.muhammhassan.reminderobat.domain.usecase.RegisterUseCase
import com.muhammhassan.reminderobat.ui.component.ButtonType
import com.muhammhassan.reminderobat.utils.DialogData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class RegisterViewModel(private val useCase: RegisterUseCase) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<String>?> = MutableStateFlow(null)
    val uiState = _uiState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    private val _isDialogShow = MutableStateFlow(false)
    val isDialogShow = _isDialogShow.asStateFlow()

    private val _dialogData = MutableStateFlow(DialogData.init())
    val dialogData = _dialogData.asStateFlow()

    fun setEmail(value: String) {
        _email.value = value
    }

    fun setName(value: String) {
        _name.value = value
    }

    fun setPassword(value: String) {
        _password.value = value
    }

    fun setConfirmPassword(value: String) {
        _confirmPassword.value = value
    }

    fun setError(message: String){
        _dialogData.value = DialogData(
            buttonType = ButtonType.NEUTRAL,
            title = "Pemberitahuan",
            message = message,
            onNeutralAction = {
                _isDialogShow.value = false
            })
        _isDialogShow.value = true
    }

    fun save() {
        viewModelScope.launch {
            if (email.value.isEmpty()) {
                _dialogData.value = DialogData(
                    buttonType = ButtonType.NEUTRAL,
                    title = "Pemberitahuan",
                    message = "Email tidak boleh kosong",
                    onNeutralAction = {
                        _isDialogShow.value = false
                    })
                _isDialogShow.value = true
                return@launch
            }
            if (!PatternsCompat.EMAIL_ADDRESS.matcher(email.value).matches()) {
                _dialogData.value = DialogData(
                    buttonType = ButtonType.NEUTRAL,
                    title = "Pemberitahuan",
                    message = "Pastikan kembali format email sudah sesuai",
                    onNeutralAction = {
                        _isDialogShow.value = false
                    })
                _isDialogShow.value = true
                return@launch
            }

            if (password.value != confirmPassword.value) {
                _dialogData.value = DialogData(
                    title = "Pemberitahuan",
                    message = "Konfirmasi password tidak sesuai dengan password yang dituliskan",
                    buttonType = ButtonType.NEUTRAL,
                    onNeutralAction = {
                        _isDialogShow.value = false
                    }
                )
                _isDialogShow.value = true
                return@launch
            }

            useCase.register(name.value, email.value, password.value).collect {
                _uiState.value = it
            }
        }
    }
}