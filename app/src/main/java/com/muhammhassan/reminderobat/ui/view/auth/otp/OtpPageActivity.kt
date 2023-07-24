package com.muhammhassan.reminderobat.ui.view.auth.otp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muhammhassan.reminderobat.domain.model.ResetTokenModel
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.ui.view.auth.reset.password.ChangePasswordActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtpPageActivity : ComponentActivity() {
    private val viewModel by viewModel<OtpPageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("token", ResetTokenModel::class.java)
        } else {
            intent.getParcelableExtra("token")
        }
        data?.let { viewModel.setToken(it) }

        setContent {
            ReminderObatTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                    val tokenState by viewModel.tokenState.collectAsStateWithLifecycle()
                    val isDialogShow by viewModel.isDialogShow.collectAsStateWithLifecycle()
                    val dialogData by viewModel.dialogData.collectAsStateWithLifecycle()
                    val otpValue by viewModel.otp.collectAsStateWithLifecycle()
                    val time by viewModel.time.collectAsStateWithLifecycle()

                    OtpPageView(
                        uiState = uiState,
                        onDialogDismiss = viewModel::hideDialog,
                        setDialogData = viewModel::setErrorMessage,
                        isDialogShow = isDialogShow,
                        dialogData = dialogData,
                        onResponseSuccess = {
                            val intent =
                                Intent(this@OtpPageActivity, ChangePasswordActivity::class.java)
                            intent.putExtra("token", it)
                            startActivity(intent)
                            finish()
                        },
                        onNavigateUp = {
                            finish()
                        },
                        onTextChanged = viewModel::setOtp,
                        otpValue = otpValue,
                        remainResendTime = time,
                        onTokenFilled = viewModel::verifyOtp,
                        onTokenResend = viewModel::resendOtp,
                        tokenState = tokenState,
                        onTokenReceived = viewModel::setToken
                    )
                }
            }
        }
    }
}