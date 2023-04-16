package com.muhammhassan.reminderobat.ui.view.alarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme

class ReminderDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = intent.data
        val id = data?.getQueryParameter("id")?.toInt() ?: 0
        setContent {
            ReminderObatTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ReminderDetailView(onFinished = {
                        finishAffinity()
                    }, id = id)
                }
            }
        }
    }
}