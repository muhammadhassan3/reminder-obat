package com.muhammhassan.reminderobat.ui.view.consultation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.muhammhassan.reminderobat.domain.model.ChatModel
import com.muhammhassan.reminderobat.domain.model.UiState
import com.muhammhassan.reminderobat.domain.usecase.ConsultationUseCase
import com.muhammhassan.reminderobat.ui.component.ButtonType
import com.muhammhassan.reminderobat.utils.DialogData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class ConsultationViewModel(private val useCase: ConsultationUseCase): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<String>>(UiState.Success(""))
    val uiState = _uiState.asStateFlow()

    val messageList = mutableStateListOf<ChatModel>()

    private val _isDialogShow = MutableStateFlow(false)
    val isDialogShow = _isDialogShow.asStateFlow()

    private val _dialogData = MutableStateFlow(DialogData.init())
    val dialogData = _dialogData.asStateFlow()

    private val _userEmail = MutableStateFlow("")
    val userEmail = _userEmail.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    private val database = Firebase.database
    private val authuser = Firebase.auth.currentUser

    private val eventListener = object: ChildEventListener{
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val gson = Gson()
            val json = gson.toJson(snapshot.value)
            val data = gson.fromJson(json, ChatModel::class.java)
            data.id = snapshot.key
            messageList.add(data)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            Timber.i("Child changed")
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            Timber.i("Child Removed")
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            Timber.i("Child Moved")
        }

        override fun onCancelled(error: DatabaseError) {
            Timber.i("Database canceled :${error.message}")
        }

    }
    init {
        if(authuser != null){
            _userEmail.value = authuser.email!!
            database.reference.child("chat").child(authuser.uid).addChildEventListener(eventListener)
        }else{
            _dialogData.value = DialogData(
                title = "Pemberitahuan",
                message = "Gagal memuat data user, silahkan coba kembali",
                buttonType = ButtonType.NEUTRAL,
                onNeutralAction = {
                    _isDialogShow.value = false
                }
            )
            _isDialogShow.value = true
        }
    }

    fun onMessageSend(){
        viewModelScope.launch {
            useCase.sendMessage(message.value).collect{
                _uiState.value = it
            }
        }
        _message.value = ""
    }

    fun setDraft(value: String){
        _message.value = value
    }

    override fun onCleared() {
        super.onCleared()
        database.reference.removeEventListener(eventListener)
    }
}