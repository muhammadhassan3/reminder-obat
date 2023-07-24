package com.muhammhassan.reminderobat.ui.view.auth.reset

import android.content.Intent
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
import com.muhammhassan.reminderobat.ui.view.auth.otp.OtpPageActivity
import org.koin.androidx.compose.koinViewModel


class UseEmailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ReminderObatTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    val viewModel = koinViewModel<UseEmailViewModel>()
                    val email by viewModel.email.collectAsStateWithLifecycle()
                    val isDialogShow by viewModel.isDialogShow.collectAsStateWithLifecycle()
                    val dialogData by viewModel.dialogData.collectAsStateWithLifecycle()
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                    UseEmailView(
                        email = email,
                        dialogData = dialogData,
                        onTextChanged = viewModel::setEmail,
                        onNavigateUp = {
                            finish()
                        },
                        isDialogShow = isDialogShow,
                        onSubmit = viewModel::sendEmail,
                        onDialogDismiss = viewModel::hideDialog,
                        setDialogData = viewModel::setDialogData,
                        uiState = uiState,
                        onResponseSuccess = {
                            navigateToOtp(it)
                        }
                    )
                }
            }
        }
    }

    private fun navigateToOtp(token: ResetTokenModel){
        val intent = Intent(this, OtpPageActivity::class.java)
        intent.putExtra("token", token)
        startActivity(intent)
        finish()
    }
}