package com.muhammhassan.reminderobat.ui.view.auth.login

import androidx.core.util.PatternsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.muhammhassan.reminderobat.domain.model.UiState
import com.muhammhassan.reminderobat.domain.usecase.LoginUseCase
import com.muhammhassan.reminderobat.ui.component.ButtonType
import com.muhammhassan.reminderobat.utils.DialogData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val useCase: LoginUseCase) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Any>?> = MutableStateFlow(null)
    val uiState = _uiState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber = _phoneNumber.asStateFlow()

    private val _isDialogShow = MutableStateFlow(false)
    val isDialogShow = _isDialogShow.asStateFlow()

    private val _dialogData = MutableStateFlow(
        DialogData(
            title = "Peringatan",
            message = "Body",
            buttonType = ButtonType.NEUTRAL
        )
    )
    val dialogData = _dialogData.asStateFlow()

    private val firebase by lazy {
        Firebase.auth
    }

    fun setEmail(value: String) {
        _email.value = value
    }

    fun setPhoneNumber(value: String) {
        _phoneNumber.value = value
    }

    fun setPassword(value: String) {
        _password.value = value
    }

    fun setErrorMessage(message: String) {
        _dialogData.value = DialogData(
            buttonType = ButtonType.NEUTRAL,
            title = "Pemberitahuan",
            message = message,
            onNeutralAction = {
                _isDialogShow.value = false
            })
        _isDialogShow.value = true
    }

    fun login(loginMethod: LoginMethod) {
        if (email.value.isEmpty() && loginMethod == EMAIL) {
            _dialogData.value = DialogData(
                buttonType = ButtonType.NEUTRAL,
                title = "Pemberitahuan",
                message = "Email tidak boleh kosong",
                onNeutralAction = {
                    _isDialogShow.value = false
                })
            _isDialogShow.value = true
            return
        }

        if (phoneNumber.value.length < 10) {
            _dialogData.value = DialogData(
                title = "Pemberitahuan",
                message = "Pastikan kembali nomor telepon yang kamu gunakan sudah sesuai",
                buttonType = ButtonType.NEUTRAL,
                onNeutralAction = {
                    _isDialogShow.value = false
                }
            )
            _isDialogShow.value = true

            return
        }
        if (!PatternsCompat.EMAIL_ADDRESS.matcher(email.value).matches() && loginMethod == EMAIL) {
            _dialogData.value = DialogData(
                buttonType = ButtonType.NEUTRAL,
                title = "Pemberitahuan",
                message = "Pastikan kembali format email sudah sesuai",
                onNeutralAction = {
                    _isDialogShow.value = false
                })
            _isDialogShow.value = true
            return
        }

        if (password.value.isEmpty()) {
            _dialogData.value = DialogData(
                buttonType = ButtonType.NEUTRAL,
                title = "Pemberitahuan",
                message = "Password tidak boleh kosong",
                onNeutralAction = {
                    _isDialogShow.value = false
                })
            _isDialogShow.value = true
            return
        }

        _uiState.value = UiState.Loading
        when (loginMethod) {
            PHONE_NUMBER -> {
                viewModelScope.launch {
                    val phoneNumberFormatted = if (phoneNumber.value.startsWith("0")) {
                        phoneNumber.value.replace("0", "+62")
                    } else if (phoneNumber.value.startsWith("62")) {
                        phoneNumber.value.replace("62", "+62")
                    } else if (phoneNumber.value.startsWith("+62")) {
                        phoneNumber.value
                    } else {
                        "+62${phoneNumber.value}"
                    }
                    useCase.login(phoneNumberFormatted).collect {
                        when (it) {
                            is UiState.Error -> _uiState.value = it
                            UiState.Loading -> {}
                            is UiState.Success -> proceedLogin(it.data, password = password.value)
                        }
                    }
                }
            }

            EMAIL -> {
                proceedLogin(email.value, password.value)
            }
        }
    }

    private fun proceedLogin(email: String, password: String) {
        firebase.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            it.user?.getIdToken(false)?.addOnSuccessListener {
                viewModelScope.launch {
                    if (it.token == null) {
                        UiState.Error("Gagal mendapatkan token")
                    } else {
                        useCase.saveToken(it.token!!)
                        _uiState.value = UiState.Success(Any())
                    }
                }
            }
        }.addOnFailureListener {
            _uiState.value = UiState.Error(it.message.toString())
        }
    }
}