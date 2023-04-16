package com.muhammhassan.reminderobat.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.muhammhassan.reminderobat.R
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme

@Composable
fun ButtonAddDrug(
    modifier: Modifier = Modifier,
    onAddButtonClicked: () -> Unit,
    onProgressButtonClicked: () -> Unit
) {
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
                OutlinedButton(
                    modifier = Modifier.size(55.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                    shape = CircleShape,
                    onClick = {
                        onProgressButtonClicked.invoke()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Image(
                        modifier = Modifier,
                        contentDescription = "Lihat Progress",
                        painter = painterResource(id = R.drawable.icon_progress),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    modifier = Modifier.size(55.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                    shape = CircleShape,
                    onClick = {
                        onAddButtonClicked.invoke()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Image(
                        modifier = Modifier,
                        painter = painterResource(id = R.drawable.pill),
                        contentDescription = "Tampilkan menu",
                    )
                }
            }
        }
        if (isButtonShowed.value) Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            modifier = Modifier
                .size(55.dp),
            shape = CircleShape,
            onClick = {
                isButtonShowed.value = !isButtonShowed.value
            }, colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White
            )) {
            Crossfade(targetState = isButtonShowed.value) { isShow ->
                if (isShow) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Tampilkan menu",
                        tint = MaterialTheme.colors.primary
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Tampilkan menu",
                        tint = MaterialTheme.colors.primary
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
        ButtonAddDrug(onAddButtonClicked = {}, onProgressButtonClicked = {})
    }
}