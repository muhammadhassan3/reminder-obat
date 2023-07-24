package com.muhammhassan.reminderobat.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme

@Composable
fun PinField(
    length: Int,
    isHide: Boolean,
    value: String,
    onTextChanged: (value: String) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(value = value,
        onValueChange = onTextChanged,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = if (isHide) KeyboardType.NumberPassword else KeyboardType.Number),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.SpaceAround) {
                repeat(length) { index ->
                    CharView(position = index, text = value)
                }
            }
        })
}

@Composable
fun CharView(position: Int, text: String, modifier: Modifier = Modifier) {
    val isFocused = text.length == position
    val char = when {
        position == text.length -> "-"
        position > text.length -> ""
        else -> text[position].toString()
    }

    Text(
        text = char,
        modifier = modifier
            .width(50.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(Color.White)
            .border(
                1.dp, if (isFocused) Color.DarkGray else Color.LightGray, RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        style = MaterialTheme.typography.h4,
        color = if (isFocused) Color.LightGray else Color.DarkGray,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
fun CharPreview() {
    ReminderObatTheme {
        CharView(position = 1, text = "1")
    }
    
}