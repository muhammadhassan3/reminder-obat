package com.muhammhassan.reminderobat.utils

data class DialogData(
    val title: String,
    val message: String,
    val buttonType: Int,
    val onNeutralAction: () -> Unit = {},
    val onConfirmAction: () -> Unit = {},
    val onCancelAction: () -> Unit = {}
)
