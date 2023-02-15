package com.muhammhassan.reminderobat.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme

@Composable
fun ButtonBack(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(modifier = modifier.size(55.dp), onClick = {onClick.invoke()}) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Kembali ke halaman sebelumnya")
    }
}

@Preview
@Composable
fun ButtonBackPreview() {
    ReminderObatTheme {
        ButtonBack {

        }
    }
}