package com.muhammhassan.reminderobat.ui.view.auth.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.muhammhassan.reminderobat.utils.DialogData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {
    private val firebase = Firebase.auth

    private val _isDialogShow = MutableStateFlow(false)
    val isDialogShow = _isDialogShow.asStateFlow()

    private val _dialogData = MutableStateFlow(DialogData.init())
    val dialogData = _dialogData.asStateFlow()

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    init {
        _username.value = firebase.currentUser?.displayName ?: ""
        _email.value = firebase.currentUser?.email ?: ""
    }

    fun setDialog(dialogData: DialogData) {
        _dialogData.value = dialogData
        _isDialogShow.value = true
    }

    fun hideDialog(){
        _isDialogShow.value = false
    }

    fun logout(onLogOutSuccess: () -> Unit) {
        firebase.signOut()
        onLogOutSuccess.invoke()
    }
}