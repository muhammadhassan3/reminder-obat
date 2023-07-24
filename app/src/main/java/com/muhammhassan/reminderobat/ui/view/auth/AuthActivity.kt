package com.muhammhassan.reminderobat.ui.view.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.ui.view.auth.reset.UseEmailActivity
import com.muhammhassan.reminderobat.ui.view.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity : ComponentActivity() {

    private val viewModel by viewModel<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.splashLoading.value
            }
        }
        setContent {
            ReminderObatTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    val isDialogShow by viewModel.isDialogShow.collectAsStateWithLifecycle()
                    val dialogData by viewModel.dialogData.collectAsStateWithLifecycle()
                    AuthView(containerDialogData = dialogData,
                        containerDialogShow = isDialogShow,
                        navigateToMainActivity = { navigateToMainActivity() },
                        navigateToEmailForm = { navigateToEmailForm() })
                }
            }
        }

        viewModel.getUser {
            finish()
            startActivity(intent)
        }

        viewModel.token.observe(this) {
            if (it != null) {
                viewModel.setToken(it)
                navigateToMainActivity()
            } else {
                viewModel.setSplashScreen(false)
            }
        }
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun navigateToEmailForm() {
        startActivity(Intent(this, UseEmailActivity::class.java))
    }
}