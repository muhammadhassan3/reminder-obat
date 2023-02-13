package com.muhammhassan.reminderobat.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme

@Composable
fun AlarmGroup(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {

    }
}

@Preview(showSystemUi = true)
@Composable
fun AlarmGroupPreview() {
    ReminderObatTheme {
        AlarmGroup()
    }
}