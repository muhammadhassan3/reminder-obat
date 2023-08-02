package com.muhammhassan.reminderobat.ui.view.auth.otp

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammhassan.reminderobat.domain.model.ResetTokenModel
import com.muhammhassan.reminderobat.domain.model.UiState
import com.muhammhassan.reminderobat.domain.usecase.OtpPageUseCase
import com.muhammhassan.reminderobat.utils.DialogData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class OtpPageViewModel(private val useCase: OtpPageUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<String>?>(null)
    val uiState = _uiState.asStateFlow()

    private val _tokenState = MutableStateFlow<UiState<ResetTokenModel>?>(null)
    val tokenState = _tokenState.asStateFlow()

    private val _otp = MutableStateFlow("")
    val otp = _otp.asStateFlow()

    private val _time = MutableStateFlow(0)
    val time = _time.asStateFlow()

    private val _dialogData = MutableStateFlow(DialogData.init())
    val dialogData = _dialogData.asStateFlow()

    private val _isDialogShow = MutableStateFlow(false)
    val isDialogShow = _isDialogShow.asStateFlow()

    private var timer: CountDownTimer? = null


    fun setToken(token: ResetTokenModel) {
        _tokenState.value = UiState.Success(token)
        timer = object : CountDownTimer(90*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _time.value = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
            }
        }

        timer?.start()
    }

    fun setErrorMessage(dialogData: DialogData) {
        _dialogData.value = dialogData
        _isDialogShow.value = true
    }

    fun hideDialog() {
        _isDialogShow.value = false
    }

    fun setOtp(value: String) {
        _otp.value = value
    }

    fun resendOtp() {
        viewModelScope.launch {
            tokenState.value?.let {
                when (it) {
                    is UiState.Success -> {
                        it.data.refreshToken?.let { token ->
                            useCase.resendToken(token).collect { response ->
                                _tokenState.value = response
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    fun verifyOtp() {
        viewModelScope.launch {
            tokenState.value?.let {
                when (it) {
                    is UiState.Success -> {
                        useCase.verifyToken(it.data.verifyToken, otp.value).collect { response ->
                            _uiState.value = response
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
        timer = null
    }
}