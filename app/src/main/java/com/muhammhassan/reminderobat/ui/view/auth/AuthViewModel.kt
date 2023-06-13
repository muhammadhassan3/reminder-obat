package com.muhammhassan.reminderobat.ui.view.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.muhammhassan.reminderobat.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class AuthViewModel(private val useCase: AuthUseCase) : ViewModel() {
    private val _splashLoading = MutableStateFlow(true)
    val splashLoading = _splashLoading.asStateFlow()

    private val _token = MutableLiveData<String?>()
    val token: LiveData<String?> get() = _token

    private val user = Firebase.auth

    init {
        if (user.currentUser == null) {
            _token.value = null
        } else {
            user.currentUser?.getIdToken(false)?.addOnCompleteListener {
                Timber.e("Token achieved")
                if (it.isSuccessful) {
                    _token.postValue(it.result.token)
                } else {
                    _token.value = null
                }
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