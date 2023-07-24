package com.muhammhassan.reminderobat.ui.view.auth.otp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.muhammhassan.reminderobat.domain.model.ResetTokenModel
import com.muhammhassan.reminderobat.domain.model.UiState
import com.muhammhassan.reminderobat.ui.component.ButtonBack
import com.muhammhassan.reminderobat.ui.component.ButtonType
import com.muhammhassan.reminderobat.ui.component.DialogContent
import com.muhammhassan.reminderobat.ui.component.PinField
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.utils.DialogData
import com.muhammhassan.reminderobat.utils.Utils
import timber.log.Timber

@Composable
fun OtpPageView(
    uiState: UiState<String>?,
    tokenState: UiState<ResetTokenModel>?,
    onTokenReceived: (value: ResetTokenModel) -> Unit,
    onDialogDismiss: () -> Unit,
    setDialogData: (value: DialogData) -> Unit,
    isDialogShow: Boolean,
    dialogData: DialogData,
    onResponseSuccess: (token: String) -> Unit,
    onNavigateUp: () -> Unit,
    onTextChanged: (value: String) -> Unit,
    otpValue: String,
    remainResendTime: Int,
    onTokenFilled: () -> Unit,
    onTokenResend: () -> Unit,
    modifier: Modifier = Modifier
) {
    val length by remember { mutableStateOf(4) }

    val isLoading = remember { mutableStateOf(false) }
    val tokenLoading = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = uiState, block = {
        when (uiState) {
            is UiState.Error -> {
                isLoading.value = false
                setDialogData.invoke(
                    DialogData(
                        "Pemberitahuan",
                        uiState.errorMessage,
                        ButtonType.NEUTRAL,
                        onNeutralAction = {
                            onDialogDismiss.invoke()
                        })
                )
            }

            UiState.Loading -> {
                isLoading.value = true
            }

            is UiState.Success -> {
                isLoading.value = false
                onResponseSuccess.invoke(uiState.data)
            }

            null -> {}
        }
    })

    LaunchedEffect(key1 = otpValue, block = {
        if (otpValue.length == length) {
            onTokenFilled.invoke()
        }
    })

    LaunchedEffect(key1 = tokenState, block = {
        Timber.e(tokenState.toString())
        when(tokenState){
            is UiState.Error -> {
                tokenLoading.value = false
                setDialogData.invoke(
                    DialogData(title = "Pemberitahuan",
                    message = "Gagal mengirim ulang token",
                    buttonType = ButtonType.NEUTRAL,
                    onNeutralAction = onDialogDismiss)
                )
            }
            UiState.Loading -> {
                tokenLoading.value = true
            }
            is UiState.Success -> {
                tokenLoading.value = false
                onTokenReceived.invoke(tokenState.data)
            }
            else -> {}
        }
    })

    if (isDialogShow) {
        Dialog(onDismissRequest = onDialogDismiss) {
            DialogContent(
                message = dialogData.message,
                title = dialogData.title,
                buttonType = dialogData.buttonType,
                onCancelClicked = dialogData.onCancelAction,
                onConfirmClicked = dialogData.onConfirmAction,
                onNeutralClicked = dialogData.onNeutralAction
            )
        }
    }

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (btnBack, title, subTitle, content, message, timerAction, loading) = createRefs()
        ButtonBack(onClick = onNavigateUp, modifier = Modifier.constrainAs(btnBack) {
            start.linkTo(parent.start, 16.dp)
            top.linkTo(parent.top, 16.dp)
        })

        if (isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.constrainAs(loading) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }, color = Color.White)
        } else {
            Text(text = "Masukkan Kode OTP", modifier = Modifier.constrainAs(title) {
                top.linkTo(btnBack.bottom, 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold))

            Text(
                text = "Silahkan periksa kode OTP yang kami kirimkan kepada Emailmu",
                modifier = Modifier.constrainAs(subTitle) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                },
                textAlign = TextAlign.Center
            )

            PinField(length = length,
                isHide = false,
                value = otpValue,
                onTextChanged = onTextChanged,
                modifier = Modifier.constrainAs(content) {
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(subTitle.bottom, 16.dp)
                    width = Dimension.fillToConstraints
                })
            Text(text = "Kirim ulang kode? ", modifier = Modifier.constrainAs(message) {
                top.linkTo(content.bottom, 16.dp)
                end.linkTo(timerAction.start, 4.dp)
            })
            if(tokenLoading.value){
                CircularProgressIndicator(modifier = Modifier.constrainAs(timerAction){
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(message.top)
                    bottom.linkTo(message.bottom)
                    height = Dimension.value(10.dp)
                    width = Dimension.wrapContent
                }, color = Color.White)
            }else if (remainResendTime > 0) {
                Text(
                    text = Utils.getTimeFormatFromInt(remainResendTime),
                    modifier = Modifier.constrainAs(timerAction) {
                        end.linkTo(parent.end, 16.dp)
                        top.linkTo(content.bottom, 16.dp)
                    })
            } else {
                Text(text = "Kirim ulang", modifier = Modifier
                    .constrainAs(timerAction) {
                        end.linkTo(parent.end, 16.dp)
                        top.linkTo(content.bottom, 16.dp)
                    }
                    .clickable {
                        onTokenResend.invoke()
                    })
            }
        }

    }

}

@Preview(showSystemUi = true, name = "With remaining resend timer")
@Composable
fun OtpPagePreview() {
    ReminderObatTheme {
        Surface(color = MaterialTheme.colors.primaryVariant) {
            OtpPageView(
                onNavigateUp = { /*TODO*/ },
                onTextChanged = {},
                otpValue = "124",
                onTokenFilled = {},
                remainResendTime = 20,
                onTokenResend = {},
                uiState = null,
                onDialogDismiss = {},
                isDialogShow = false,
                dialogData = DialogData.init(),
                setDialogData = {},
                onResponseSuccess = {},
                tokenState = UiState.Loading,
                onTokenReceived = {}
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun OtpPageWithButtonPreview() {
    ReminderObatTheme {
        Surface(color = MaterialTheme.colors.primaryVariant) {
            OtpPageView(
                onNavigateUp = { /*TODO*/ },
                onTextChanged = {},
                otpValue = "124",
                onTokenFilled = {},
                remainResendTime = 0,
                onTokenResend = {},
                uiState = null,
                onDialogDismiss = {},
                isDialogShow = false,
                dialogData = DialogData.init(),
                setDialogData = {},
                onResponseSuccess = {},
                tokenState = UiState.Loading,
                onTokenReceived = {}
            )
        }
    }
}