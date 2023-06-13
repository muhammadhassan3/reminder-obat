package com.muhammhassan.reminderobat.ui.view.auth.register

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.muhammhassan.reminderobat.domain.model.UiState
import com.muhammhassan.reminderobat.ui.component.ButtonBack
import com.muhammhassan.reminderobat.ui.component.ButtonType
import com.muhammhassan.reminderobat.ui.component.DialogContent
import com.muhammhassan.reminderobat.ui.component.InputField
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.utils.DialogData
import compose.icons.Octicons
import compose.icons.octicons.Eye24
import compose.icons.octicons.EyeClosed24
import timber.log.Timber

@Composable
fun RegisterView(
    data: UiState<String>?,
    onNavUp: () -> Unit,
    name: String,
    onNameChanged: (value: String) -> Unit,
    email: String,
    onEmailChanged: (value: String) -> Unit,
    password: String,
    onPasswordChanged: (value: String) -> Unit,
    onSaveClicked: () -> Unit,
    confirmPassword: String,
    onConfirmPasswordChanged: (value: String) -> Unit,
    isDialogShow: Boolean,
    dialogData: DialogData,
    onErrorResponse: (message: String) -> Unit,
    onSuccessResponse: (message: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isLoading = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = data, block = {
        when (data) {
            is UiState.Error -> {
                isLoading.value = false
                onErrorResponse.invoke(data.errorMessage)
            }

            UiState.Loading -> isLoading.value = true
            is UiState.Success -> {
                isLoading.value = false
                onSuccessResponse.invoke(data.data)
            }

            else -> {}
        }
    })

    val isPasswordShow = remember {
        mutableStateOf(false)
    }
    val isConfirmPasswordShow = remember {
        mutableStateOf(false)
    }
    if (isDialogShow) {
        Dialog(
            onDismissRequest = dialogData.onNeutralAction, properties = DialogProperties(
                dismissOnBackPress = false,
                usePlatformDefaultWidth = true
            )
        ) {
            DialogContent(message = dialogData.message,
                title = dialogData.title,
                buttonType = dialogData.buttonType,
                onNeutralClicked = {
                    dialogData.onNeutralAction.invoke()
                })
        }
    }

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (btnNavUp, title, content, btnRegister, loading) = createRefs()
        if (isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.constrainAs(loading) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                top.linkTo(parent.top)
            }, color = Color.White)
        } else {
            ButtonBack(onClick = onNavUp, modifier = Modifier.constrainAs(btnNavUp) {
                top.linkTo(parent.top, 16.dp)
                start.linkTo(parent.start, 16.dp)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            })
            Text(
                "Daftar", modifier = Modifier.constrainAs(title) {
                    top.linkTo(btnNavUp.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.preferredWrapContent
                    height = Dimension.preferredWrapContent
                }, style = TextStyle(
                    color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold
                )
            )
            Card(modifier = Modifier.constrainAs(content) {
                top.linkTo(title.bottom, 16.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }, shape = RoundedCornerShape(16.dp)) {
                ConstraintLayout(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    val (edtName, edtEmail, edtPassword, edtConfirmpassword, edtConfirmPassword) = createRefs()
                    InputField(
                        title = "Nama",
                        onTextChanged = onNameChanged,
                        value = name,
                        modifier = Modifier.constrainAs(edtName) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                        singleLine = true,
                        inputType = KeyboardType.Email
                    )
                    InputField(
                        title = "Email",
                        onTextChanged = onEmailChanged,
                        value = email,
                        modifier = Modifier.constrainAs(edtEmail) {
                            top.linkTo(edtName.bottom, 8.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                        singleLine = true,
                        inputType = KeyboardType.Email
                    )

                    OutlinedTextField(value = password,
                        onValueChange = onPasswordChanged,
                        modifier = Modifier.constrainAs(edtPassword) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(edtEmail.bottom, 8.dp)
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
                                    imageVector = Octicons.Eye24,
                                    contentDescription = "Perlihatkan"
                                )
                            }
                        })

                    OutlinedTextField(value = confirmPassword,
                        onValueChange = onConfirmPasswordChanged,
                        modifier = Modifier.constrainAs(edtConfirmPassword) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(edtPassword.bottom, 8.dp)
                            width = Dimension.fillToConstraints
                        },
                        singleLine = true,
                        visualTransformation = if (isConfirmPasswordShow.value) VisualTransformation.None else PasswordVisualTransformation(),
                        label = { Text(text = "Konfirmasi Password") },
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
                                    imageVector = Octicons.Eye24,
                                    contentDescription = "Perlihatkan"
                                )
                            }
                        })
                }
            }
            Button(onClick = onSaveClicked, modifier = Modifier.constrainAs(btnRegister) {
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                bottom.linkTo(parent.bottom, 16.dp)
                width = Dimension.fillToConstraints
                height = Dimension.value(45.dp)
            }) {
                Text(text = "Daftar")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun RegisterPreview() {
    ReminderObatTheme {
        Surface(color = MaterialTheme.colors.background) {
            RegisterView(
                data = UiState.Success(""),
                onNavUp = {},
                onEmailChanged = {},
                onNameChanged = {},
                onPasswordChanged = {},
                onConfirmPasswordChanged = {},
                onSaveClicked = {},
                name = "",
                email = "",
                password = "",
                confirmPassword = "",
                isDialogShow = false,
                onErrorResponse = {},
                dialogData = DialogData("Title", "Message", ButtonType.NEUTRAL),
                onSuccessResponse = {}
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun RegisterPreviewWithDialog() {
    ReminderObatTheme {
        Surface(color = MaterialTheme.colors.background) {
            RegisterView(
                data = UiState.Error("Error"),
                onNavUp = {},
                onEmailChanged = {},
                onNameChanged = {},
                onPasswordChanged = {},
                onConfirmPasswordChanged = {},
                onSaveClicked = {},
                name = "",
                email = "",
                password = "",
                confirmPassword = "",
                isDialogShow = true,
                onErrorResponse = {},
                dialogData = DialogData("Title", "Message", ButtonType.NEUTRAL),
                onSuccessResponse = {}
            )
        }
    }
}