package com.muhammhassan.reminderobat.ui.view.splash

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.muhammhassan.reminderobat.R
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.ui.view.Screen
import com.muhammhassan.reminderobat.ui.view.main.MainActivity
import kotlinx.coroutines.delay

@Composable
fun SplashView(modifier: Modifier = Modifier, onRedirect: () -> Unit) {
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = Modifier
                .height(150.dp)
                .width(150.dp),
            painter = painterResource(id = R.drawable.pills),
            contentDescription = stringResource(R.string.app_icon_desc)
        )

        LaunchedEffect(key1 = true){
            delay(1500)
            onRedirect.invoke()
        }
    }
}