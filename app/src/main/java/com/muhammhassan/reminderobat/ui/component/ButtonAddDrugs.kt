package com.muhammhassan.reminderobat.ui.component

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.muhammhassan.reminderobat.ui.theme.Purple500
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme

@Composable
fun ButtonAddDrug(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val isButtonShowed = remember {
        mutableStateOf(false)
    }
    Column(modifier = modifier) {
        AnimatedVisibility(
            visible = isButtonShowed.value,
            enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut()
        ) {
            Column(modifier = Modifier) {

                OutlinedButton(modifier = Modifier
                    .size(55.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    shape = CircleShape,
                    onClick = {
                        onClick.invoke()
                    }) {
                    Image(
                        modifier = Modifier,
                        painter = painterResource(id = com.muhammhassan.reminderobat.R.drawable.pill),
                        contentDescription = "Tampilkan menu",
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
        IconButton(modifier = Modifier
            .size(55.dp)
            .clip(CircleShape)
            .background(Color.White), onClick = {
            isButtonShowed.value = !isButtonShowed.value
        }) {
            Crossfade(targetState = isButtonShowed.value) { isShow ->
                if(isShow){
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Tampilkan menu",
                        tint = Purple500
                    )
                }else{
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Tampilkan menu",
                        tint = Purple500
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ButtonAddDrugPreview() {
    ReminderObatTheme {
        ButtonAddDrug {

        }
    }
}