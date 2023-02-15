package com.muhammhassan.reminderobat.ui.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.muhammhassan.reminderobat.ui.theme.Blue50p
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme

@Composable
fun DaySelector(
    modifier: Modifier = Modifier,
    onItemSelected: (value: Boolean) -> Unit,
    text: String,
    baseColor: Color = Color.Gray
) {
    val isSelected = remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        Crossfade(targetState = isSelected.value) {
            if (it) {
                OutlinedButton(
                    modifier = Modifier.size(45.dp), onClick = {
                        isSelected.value = !isSelected.value
                        onItemSelected.invoke(isSelected.value)
                    }, shape = CircleShape, border = BorderStroke(2.dp, Blue50p)
                ) {
                    Text(modifier = Modifier, text = text)
                }
            } else {
                OutlinedButton(
                    modifier = Modifier.size(45.dp), onClick = {
                        isSelected.value = !isSelected.value
                        onItemSelected.invoke(isSelected.value)
                    }, shape = CircleShape, border = BorderStroke(2.dp, baseColor)
                ) {
                    Text(modifier = Modifier, text = text)
                }
            }
        }
    }
}

@Preview
@Composable
fun DaySelectorPreview() {
    ReminderObatTheme {
        DaySelector(onItemSelected = {}, text = "S")
    }

}