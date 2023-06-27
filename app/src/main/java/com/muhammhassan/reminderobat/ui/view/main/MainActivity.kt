package com.muhammhassan.reminderobat.ui.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.ui.view.auth.AuthActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReminderObatTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    MainView(onNavBarChangeColor = {color ->
                        setNavColor(color)
                    }, openLoginPage = {
                        navigateToLogin()
                    })
                }
            }
        }

    }

    private fun setNavColor(color: Color){
        window.navigationBarColor = color.toArgb()
    }

    private fun navigateToLogin(){
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}