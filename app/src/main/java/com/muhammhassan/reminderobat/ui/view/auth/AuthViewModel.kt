package com.muhammhassan.reminderobat.ui.view.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.muhammhassan.reminderobat.domain.usecase.AuthUseCase
import com.muhammhassan.reminderobat.ui.component.ButtonType
import com.muhammhassan.reminderobat.utils.DialogData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val useCase: AuthUseCase) : ViewModel() {
    private val _splashLoading = MutableStateFlow(true)
    val splashLoading = _splashLoading.asStateFlow()

    private val _token = MutableLiveData<String?>()
    val token: LiveData<String?> get() = _token

    private val _isDialogShow = MutableStateFlow(false)
    val isDialogShow = _isDialogShow.asStateFlow()

    private val _dialogData = MutableStateFlow(DialogData.init())
    val dialogData = _dialogData.asStateFlow()

    private val user = Firebase.auth

    fun getUser(onNeutralClicked: () -> Unit) {
        if (user.currentUser == null) {
            _token.value = null
        } else {
            user.currentUser?.getIdToken(true)?.addOnSuccessListener {
                if (it.token != null) {
                    _token.postValue(it.token)
                } else {
                    _token.value = null
                }
            }?.addOnFailureListener {
                _dialogData.value = DialogData(
                    title = "Pemberitahuan",
                    message = "Gagal memuat data pengguna. Silahkan coba beberapa saat lagi.",
                    buttonType = ButtonType.NEUTRAL,
                    onNeutralAction = {
                        _isDialogShow.value = false
                        onNeutralClicked.invoke()
                    }
                )
                _isDialogShow.value = true
                _token.value = null
            }
        }
    }

    fun setSplashScreen(value: Boolean) {
        _splashLoading.value = value
    }

    fun setToken(value: String) {
        viewModelScope.launch {
            useCase.setToken(value)
        }
    }
}