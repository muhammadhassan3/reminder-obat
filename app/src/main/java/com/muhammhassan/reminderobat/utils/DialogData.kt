package com.muhammhassan.reminderobat.utils

import com.muhammhassan.reminderobat.ui.component.ButtonType

data class DialogData(
    val title: String,
    val message: String,
    val buttonType: Int,
    val onNeutralAction: () -> Unit = {},
    val onConfirmAction: () -> Unit = {},
    val onCancelAction: () -> Unit = {}
) {
    companion object {
        fun init() = DialogData("Title", "Message", ButtonType.NEUTRAL)
    }
}
