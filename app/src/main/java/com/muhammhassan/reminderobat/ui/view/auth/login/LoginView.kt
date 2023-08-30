package com.muhammhassan.reminderobat.ui.view.auth.login

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
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
import com.muhammhassan.reminderobat.ui.component.DialogContent
import com.muhammhassan.reminderobat.ui.component.InputField
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.utils.DialogData
import compose.icons.Octicons
import compose.icons.octicons.DeviceMobile24
import compose.icons.octicons.Eye24
import compose.icons.octicons.EyeClosed24
import compose.icons.octicons.Mail24
import timber.log.Timber

@Composable
fun LoginView(
    data: UiState<Any>?,
    email: String,
    onEmailChanged: (value: String) -> Unit,
    password: String,
    onPasswordChanged: (value: String) -> Unit,
    phoneNumber: String,
    onPhoneNumberChanged: (value: String) -> Unit,
    onSaveClicked: (loginMethod: LoginMethod) -> Unit,
    onRegisterClicked: () -> Unit,
    isDialogShow: Boolean,
    dialogData: DialogData,
    onSuccessResponse: () -> Unit,
    onErrorResponse: (String) -> Unit,
    onResetPasswordClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isPasswordShow = remember {
        mutableStateOf(false)
    }

    val isLoading = remember {
        mutableStateOf(false)
    }

    val loginMethod = remember {
        mutableStateOf(EMAIL)
    }

    LaunchedEffect(key1 = data, block = {
        when (data) {
            is UiState.Error -> {
                isLoading.value = false
                onErrorResponse.invoke(data.errorMessage)
            }

            UiState.Loading -> isLoading.value = true
            is UiState.Success -> {
                onSuccessResponse.invoke()
                isLoading.value = false
            }

            null -> {}
        }
    })

    if (isDialogShow) {
        Dialog(
            onDismissRequest = dialogData.onNeutralAction, properties = DialogProperties(
                dismissOnBackPress = false, usePlatformDefaultWidth = true
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
        val (title, content, btnLogin, btnRegister, loading, loginMethodSelector) = createRefs()
        if (isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.constrainAs(loading) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                top.linkTo(parent.top)
            }, color = Color.White)
        } else {
            Text(
                text = "Masuk", modifier = Modifier.constrainAs(title) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, 20.dp)
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
                height = Dimension.preferredWrapContent
            }, shape = RoundedCornerShape(16.dp), backgroundColor = Color.White) {
                ConstraintLayout(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .animateContentSize()
                ) {
                    val (inputLayout, tvResetPassword) = createRefs()
                    Column(modifier = Modifier.constrainAs(inputLayout) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        width = Dimension.fillToConstraints
                    }) {
                        Crossfade(
                            targetState = loginMethod.value, label = "Login Options"
                        ) { method ->
                            when (method) {
                                EMAIL -> {
                                    InputField(
                                        title = "Email",
                                        onTextChanged = onEmailChanged,
                                        value = email,
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true,
                                        inputType = KeyboardType.Email
                                    )
                                }

                                PHONE_NUMBER -> {
                                    OutlinedTextField(value = phoneNumber,
                                        onValueChange = onPhoneNumberChanged,
                                        modifier = Modifier.fillMaxWidth(),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                        singleLine = true,
                                        label = {
                                            Text(text = "Nomor Telepon")
                                        },
                                        placeholder = {
                                            Text(text = "Contoh : 8123456789")
                                        },
                                        leadingIcon = {
                                            Row(modifier = Modifier) {
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(text = "+62")
                                                Spacer(modifier = Modifier.width(8.dp))
                                            }
                                        })
                                }
                            }
                        }


                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(value = password,
                            onValueChange = onPasswordChanged,
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            visualTransformation = if (isPasswordShow.value) VisualTransformation.None else PasswordVisualTransformation(),
                            label = { Text(text = "Password") },
                            trailingIcon = {
                                IconButton(onClick = {
                                    isPasswordShow.value = !isPasswordShow.value
                                }) {
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
                    }

                    Text(text = "Lupa Password?",
                        modifier
                            .constrainAs(tvResetPassword) {
                                top.linkTo(inputLayout.bottom, 16.dp)
                                end.linkTo(parent.end)
                            }
                            .clickable {
                                onResetPasswordClicked.invoke()
                            })
                }
            }

            Column(modifier = Modifier.constrainAs(loginMethodSelector) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(content.bottom, 32.dp)
            }, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Masuk menggunakan")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = { loginMethod.value = PHONE_NUMBER }, modifier = Modifier
                            .clip(
                                CircleShape
                            )
                            .background(Color.White)
                            .padding(4.dp)
                    ) {
                        Icon(
                            painter = rememberVectorPainter(image = Octicons.DeviceMobile24),
                            contentDescription = "Nomor hp",
                            modifier = Modifier.size(35.dp),
                            tint = Color.Black
                        )
                    }
                    IconButton(
                        onClick = { loginMethod.value = EMAIL }, modifier = Modifier
                            .clip(
                                CircleShape
                            )
                            .background(Color.White)
                            .padding(4.dp)
                    ) {
                        Icon(
                            painter = rememberVectorPainter(image = Octicons.Mail24),
                            contentDescription = "Email",
                            modifier = Modifier.size(35.dp),
                            tint = Color.Black
                        )
                    }
                }
            }

            Button(onClick = {
                Timber.e("LoginView: ${loginMethod.value}")
                onSaveClicked.invoke(loginMethod.value)
            }, modifier = Modifier.constrainAs(btnLogin) {
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                bottom.linkTo(btnRegister.top, 8.dp)
                width = Dimension.fillToConstraints
                height = Dimension.value(45.dp)
            }) {
                Text(text = "Masuk")
            }

            TextButton(
                onClick = { onRegisterClicked.invoke() },
                modifier = Modifier.constrainAs(btnRegister) {
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(45.dp)
                },
            ) {
                Text(
                    text = "Belum punya akun? Daftar disini", style = TextStyle(color = Color.White)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginPreview() {
    ReminderObatTheme {
        Surface(color = MaterialTheme.colors.background) {
            LoginView(onSaveClicked = {},
                onPasswordChanged = {},
                onEmailChanged = {},
                email = "",
                password = "",
                onRegisterClicked = {},
                dialogData = DialogData.init(),
                isDialogShow = false,
                data = null,
                onErrorResponse = {},
                onSuccessResponse = {},
                onResetPasswordClicked = {},
                phoneNumber = "8123456789",
                onPhoneNumberChanged = {})
        }
    }
}