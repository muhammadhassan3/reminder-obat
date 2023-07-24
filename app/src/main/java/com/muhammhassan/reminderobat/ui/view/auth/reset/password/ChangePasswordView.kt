package com.muhammhassan.reminderobat.ui.view.auth.reset.password

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.muhammhassan.reminderobat.domain.model.UiState
import com.muhammhassan.reminderobat.ui.component.ButtonBack
import com.muhammhassan.reminderobat.ui.component.ButtonType
import com.muhammhassan.reminderobat.ui.component.DialogContent
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.utils.DialogData
import compose.icons.Octicons
import compose.icons.octicons.Eye24
import compose.icons.octicons.EyeClosed24

@Composable
fun ChangePasswordView(
    uiState: UiState<String>?,
    isDialogShow: Boolean,
    dialogData: DialogData,
    showError: (data: DialogData) -> Unit,
    onDialogDismiss: () -> Unit,
    onNavigateUp: () -> Unit,
    onSaveClicked: () -> Unit,
    onPasswordChanged: (value: String) -> Unit,
    onConfirmPasswordChanged: (value: String) -> Unit,
    passwordText: String,
    confirmPasswordText: String,
    onResponseSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isPasswordShow = remember { mutableStateOf(false) }
    val isConfirmPasswordShow = remember { mutableStateOf(false) }

    val isLoading = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = uiState, block = {
        when(uiState){
            is UiState.Error ->{
                isLoading.value = false
                showError.invoke(
                    DialogData(
                        title = "Pemberitahuan",
                        message = uiState.errorMessage,
                        buttonType = ButtonType.NEUTRAL,
                        onNeutralAction = onDialogDismiss
                    )
                )
            }
            UiState.Loading -> {
                isLoading.value = true
            }
            is UiState.Success -> {
                isLoading.value = false
                onResponseSuccess.invoke()
            }
            null -> {}
        }
    })

    if (isDialogShow) {
        Dialog(onDismissRequest = onDialogDismiss) {
            DialogContent(
                message = dialogData.message,
                title = dialogData.title,
                buttonType = dialogData.buttonType,
                onNeutralClicked = dialogData.onNeutralAction,
                onConfirmClicked = dialogData.onConfirmAction,
                onCancelClicked = dialogData.onCancelAction
            )
        }
    }

    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (btnBack, title, content, btnSave, loading) = createRefs()

        ButtonBack(onClick = onNavigateUp, modifier = Modifier.constrainAs(btnBack) {
            start.linkTo(parent.start, 16.dp)
            top.linkTo(parent.top, 16.dp)
        })
        if(isLoading.value){
            CircularProgressIndicator(modifier = Modifier.constrainAs(loading){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(btnBack.bottom)
                bottom.linkTo(parent.bottom)
            })
        }else {
            Text(
                text = "Password Baru", modifier = Modifier.constrainAs(title) {
                    top.linkTo(btnBack.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold)
            )
            Card(modifier = modifier.constrainAs(content){
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                top.linkTo(title.bottom, 16.dp)
                width = Dimension.fillToConstraints
            }, backgroundColor = Color.White, shape = RoundedCornerShape(12.dp)){
                ConstraintLayout(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    val (password, confirmPassword) = createRefs()
                    OutlinedTextField(value = passwordText,
                        onValueChange = onPasswordChanged,
                        modifier = Modifier.constrainAs(password) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            width = Dimension.fillToConstraints
                        },
                        singleLine = true,
                        visualTransformation = if (isPasswordShow.value) VisualTransformation.None else PasswordVisualTransformation(),
                        label = { Text(text = "Password") },
                        trailingIcon = {
                            IconButton(onClick = { isPasswordShow.value = !isPasswordShow.value }) {
                                if (isPasswordShow.value) {
                                    Icon(
                                        imageVector = Octicons.EyeClosed24,
                                        contentDescription = "Sembunyikan"
                                    )
                                } else Icon(
                                    imageVector = Octicons.Eye24, contentDescription = "Perlihatkan"
                                )
                            }
                        })
                    OutlinedTextField(value = confirmPasswordText,
                        onValueChange = onConfirmPasswordChanged,
                        modifier = Modifier.constrainAs(confirmPassword) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(password.bottom, 8.dp)
                            width = Dimension.fillToConstraints
                        },
                        singleLine = true,
                        visualTransformation = if (isConfirmPasswordShow.value) VisualTransformation.None else PasswordVisualTransformation(),
                        label = { Text(text = "Konfirmasi password") },
                        trailingIcon = {
                            IconButton(onClick = {
                                isConfirmPasswordShow.value = !isConfirmPasswordShow.value
                            }) {
                                if (isConfirmPasswordShow.value) {
                                    Icon(
                                        imageVector = Octicons.EyeClosed24,
                                        contentDescription = "Sembunyikan"
                                    )
                                } else Icon(
                                    imageVector = Octicons.Eye24, contentDescription = "Perlihatkan"
                                )
                            }
                        })
                }
            }


            Button(
                onClick = onSaveClicked,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.constrainAs(btnSave) {
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(45.dp)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            ) {
                Text("Simpan")
            }
        }
    }

}


@Preview(showSystemUi = true)
@Composable
fun ChangePasswordPreview() {
    ReminderObatTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            ChangePasswordView(
                uiState = null,
                isDialogShow = false,
                dialogData = DialogData.init(),
                onDialogDismiss = { /*TODO*/ },
                onNavigateUp = { /*TODO*/ },
                onSaveClicked = { /*TODO*/ },
                onPasswordChanged = {},
                onConfirmPasswordChanged = {},
                passwordText = "",
                confirmPasswordText = "",
                onResponseSuccess = {},
                showError = {}
            )
        }
    }
}