package com.muhammhassan.reminderobat.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    title: String,
    onTextChanged: (text: String) -> Unit,
    value: String,
    inputType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier,
            text = title,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { onTextChanged.invoke(it) },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = inputType),
        )
    }
}

@Preview
@Composable
fun InputFieldPreview() {
    ReminderObatTheme {
        InputField(title = "Input Title", onTextChanged = {}, value = "test")
    }
}