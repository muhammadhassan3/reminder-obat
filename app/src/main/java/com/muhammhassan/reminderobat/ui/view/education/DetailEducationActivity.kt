package com.muhammhassan.reminderobat.ui.view.education

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.muhammhassan.reminderobat.domain.model.Articles
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme

class DetailEducationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("data", Articles::class.java)
        } else {
            intent.getParcelableExtra("data")
        } ?: throw IllegalArgumentException("Data parameter null")
        setContent {
            ReminderObatTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DetailEducationView(onNavUp = { finish() }, data = data)
                }
            }
        }
    }
}