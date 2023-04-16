package com.muhammhassan.reminderobat.ui.view.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import kotlinx.coroutines.delay

@Composable
fun SplashView(modifier: Modifier = Modifier, onRedirect: () -> Unit) {

    LaunchedEffect(key1 = true){
        delay(1500)
        onRedirect.invoke()
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(100))
                .background(Color.White)
        ) {
            Image(
                modifier = Modifier.size(120.dp),
                painter = painterResource(id = com.muhammhassan.reminderobat.R.drawable.app_icon),
                contentDescription = stringResource(com.muhammhassan.reminderobat.R.string.app_icon_desc),
                contentScale = ContentScale.FillBounds
            )
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "Keep Health",
                textAlign = TextAlign.Start,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SplashPreview() {
    ReminderObatTheme {
        SplashView {

        }
    }
}