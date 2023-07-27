package com.muhammhassan.reminderobat.ui.view.add.schedule

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.muhammhassan.reminderobat.domain.model.DrugsData
import com.muhammhassan.reminderobat.navigation.ArgsName
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.ui.view.add.stock.AddStockActivity

class AddReminderActivity : ComponentActivity() {

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == RESULT_OK){
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(ArgsName.data, DrugsData::class.java)
        } else {
            intent.getParcelableExtra(ArgsName.data)
        } ?: throw IllegalArgumentException("Null Data Property")
        setContent {
            ReminderObatTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AddReminderView(onBackPressed = { finish() }, onNextPressed = {
                        val intent = Intent(this@AddReminderActivity, AddStockActivity::class.java)
                        intent.putExtra(ArgsName.data, it)
                        launcher.launch(intent)
                    }, data = data)
                }
            }
        }
    }
}