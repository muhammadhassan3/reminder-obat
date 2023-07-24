package com.muhammhassan.reminderobat.ui.view.auth.reset.password

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.ui.view.auth.AuthActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordActivity: ComponentActivity() {
    private val viewModel by viewModel<ChangePasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val token = intent.getStringExtra("token")
        if(token == null){
            Toast.makeText(this, "Pastikan kamu sudah mendapatkan token", Toast.LENGTH_SHORT).show()
        }

        token?.let { viewModel.setToken(it) }

        setContent{
            val state by viewModel.state.collectAsStateWithLifecycle()
            val isDialogShow by viewModel.isDialogShow.collectAsStateWithLifecycle()
            val dialogData by viewModel.dialogData.collectAsStateWithLifecycle()
            val password by viewModel.password.collectAsStateWithLifecycle()
            val confirmPassword by viewModel.confirmPassword.collectAsStateWithLifecycle()
            
            ReminderObatTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    ChangePasswordView(
                        uiState = state,
                        isDialogShow = isDialogShow,
                        dialogData = dialogData,
                        onDialogDismiss = viewModel::hideDialog,
                        onNavigateUp = { finish() },
                        onSaveClicked = viewModel::save,
                        onPasswordChanged = viewModel::setPassword,
                        onConfirmPasswordChanged = viewModel::setConfirmPassword,
                        passwordText = password,
                        confirmPasswordText = confirmPassword,
                        showError = viewModel::setDialogData,
                        onResponseSuccess = {onResetPasswordSuccess()}
                    )
                }
            }
        }
    }

    private fun onResetPasswordSuccess(){
        Toast.makeText(this, "Password Berhasil diperbarui", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, AuthActivity::class.java)
        finishAffinity()
        startActivity(intent)
    }
}