package com.muhammhassan.reminderobat.ui.view.education

import androidx.lifecycle.ViewModel
import com.muhammhassan.reminderobat.ui.component.ButtonType
import com.muhammhassan.reminderobat.utils.DialogData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailEducationViewModel: ViewModel() {
    private val _isDialogShow = MutableStateFlow(false)
    val isDialogShow = _isDialogShow.asStateFlow()

    private val _dialogData = MutableStateFlow(DialogData.init())
    val dialogData = _dialogData.asStateFlow()

    fun setError(message: String, action: () -> Unit){
        _dialogData.value = DialogData(
            title = "Pemberitahuan",
            message = message,
            buttonType = ButtonType.NEUTRAL,
            onNeutralAction = {
                _isDialogShow.value = false
                action.invoke()
            }
        )

        _isDialogShow.value = true
    }
}