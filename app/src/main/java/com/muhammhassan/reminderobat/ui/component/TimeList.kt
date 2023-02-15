package com.muhammhassan.reminderobat.ui.component

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun TimeList(
    modifier: Modifier = Modifier,
    context: Context,
    onTimeChanged: (value: String) -> Unit,
    position: Int = 0
) {
    val timeSelected = remember { mutableStateOf("00:00") }
    Row(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = "Pengingat ke-$position"
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    val calendar = Calendar.getInstance()
                    val hour = calendar[Calendar.HOUR_OF_DAY]
                    val minute = calendar[Calendar.MINUTE]
                    val timeDialog = TimePickerDialog(context, { _, selectedHour, selectedMinute ->
                        val text = "${if(selectedHour < 10) "0$selectedHour" else selectedHour}:${if(selectedMinute < 10) "0$selectedMinute" else selectedMinute}"
                        onTimeChanged.invoke(text)
                        timeSelected.value =
                            text
                    }, hour, minute, true)

                    timeDialog.show()
                },
            text = timeSelected.value,
            textAlign = TextAlign.End,
            fontSize = 36.sp,
            fontWeight = FontWeight.Thin
        )
    }
}