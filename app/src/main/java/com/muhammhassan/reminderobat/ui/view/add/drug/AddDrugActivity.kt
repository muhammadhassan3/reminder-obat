package com.muhammhassan.reminderobat.ui.view.add.drug

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.muhammhassan.reminderobat.navigation.ArgsName
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.ui.view.add.schedule.AddReminderActivity
import timber.log.Timber

class AddDrugActivity: ComponentActivity() {

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        Timber.e(result.resultCode.toString())
        if(result.resultCode == RESULT_OK){
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { 
            ReminderObatTheme{
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    AddDrugView(onBackPressed = {
                                                finish()
                    }, onNextPressed = {
                        val intent = Intent(this@AddDrugActivity, AddReminderActivity::class.java)
                        intent.putExtra(ArgsName.data, it)
                        launcher.launch(intent)
                    })
                }
            }
        }
    }
}