package com.muhammhassan.reminderobat.ui.view.add.stock

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.muhammhassan.reminderobat.domain.model.DrugsData
import com.muhammhassan.reminderobat.navigation.ArgsName
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme

class AddStockActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(ArgsName.data, DrugsData::class.java)
        } else {
            intent.getParcelableExtra(ArgsName.data)
        } ?: throw IllegalArgumentException("Data value is null")
        setContent {
            ReminderObatTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AddStockView(onBackPressed = { finish() }, onDataSaved = {
                        setResult(RESULT_OK)
                        finish()
                    }, data = data)
                }
            }
        }
    }
}