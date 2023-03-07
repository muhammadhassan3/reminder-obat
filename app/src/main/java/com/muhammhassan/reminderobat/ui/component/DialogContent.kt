package com.muhammhassan.reminderobat.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme

@Composable
fun DialogContent(
    modifier: Modifier = Modifier,
    message: String,
    title: String,
    buttonType: Int,
    onNeutralClicked: () -> Unit = {},
    onCancelClicked: () -> Unit = {},
    onConfirmClicked: () -> Unit = {}
) {
    Card(modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)) {
        Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = title,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = message,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (buttonType == ButtonType.NEUTRAL) {
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onNeutralClicked.invoke() },
                    border = BorderStroke(0.dp, Color.Transparent)
                ) {
                    Text(text = "Oke")
                }
            } else {
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = { onCancelClicked.invoke() },
                        border = BorderStroke(0.dp, Color.Transparent)
                    ) {
                        Text(text = "Batalkan")
                    }
                    OutlinedButton(modifier = Modifier.weight(1f),
                        onClick = { onConfirmClicked.invoke() },
                        border = BorderStroke(0.dp, Color.Transparent)
                    ) {
                        Text(text = "Lanjutkan")
                    }
                }
            }
        }
    }
}

object ButtonType {
    const val NEUTRAL = 0
    const val YES_NO = 1
}

@Preview
@Composable
fun DialogNeutralPreview() {
    ReminderObatTheme {
        DialogContent(
            message = "This is dialog message", title = "Title", buttonType = ButtonType.NEUTRAL
        )
    }
}

@Preview
@Composable
fun DialogYesNoPreview() {
    ReminderObatTheme {
        DialogContent(
            message = "This is dialog message", title = "Title", buttonType = ButtonType.YES_NO
        )
    }
}