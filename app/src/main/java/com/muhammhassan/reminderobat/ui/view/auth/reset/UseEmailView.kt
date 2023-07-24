package com.muhammhassan.reminderobat.ui.view.auth.reset

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
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
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.utils.DialogData

@Composable
fun UseEmailView(
    uiState: UiState<ResetTokenModel>?,
    onNavigateUp: () -> Unit,
    onTextChanged: (email: String) -> Unit,
    email: String,
    onSubmit: () -> Unit,
    isDialogShow: Boolean,
    dialogData: DialogData,
    setDialogData: (value: DialogData) -> Unit,
    onDialogDismiss: () -> Unit,
    onResponseSuccess: (token: ResetTokenModel) -> Unit,
    modifier: Modifier = Modifier
) {

    val isLoading = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = uiState, block = {
        when (uiState) {
            is UiState.Error -> {
                isLoading.value = false
                val message = uiState.errorMessage
                setDialogData.invoke(
                    DialogData(
                        "title",
                        message,
                        ButtonType.NEUTRAL,
                        onNeutralAction = onDialogDismiss
                    )
                )
            }

            UiState.Loading -> {
                isLoading.value = true
            }

            is UiState.Success -> {
                onResponseSuccess.invoke(uiState.data)
            }

            else -> {}
        }
    })

    if (isDialogShow) {
        Dialog(onDismissRequest = dialogData.onNeutralAction) {
            DialogContent(
                title = dialogData.title,
                message = dialogData.message,
                buttonType = ButtonType.NEUTRAL,
                onNeutralClicked = dialogData.onNeutralAction,
                onConfirmClicked = dialogData.onConfirmAction,
                onCancelClicked = dialogData.onCancelAction
            )
        }
    }
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (btnBack, title, btnNext, content, loading) = createRefs()
        ButtonBack(onClick = onNavigateUp, modifier = Modifier.constrainAs(btnBack) {
            top.linkTo(parent.top, 16.dp)
            start.linkTo(parent.start, 16.dp)
        })
        if (isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.constrainAs(loading) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }, color = Color.White)
        } else {
            Text(
                text = "Masukkan Email", modifier = Modifier.constrainAs(title) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(btnBack.top)
                    bottom.linkTo(btnBack.bottom)
                }, style = TextStyle(
                    fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White
                )
            )
            Card(
                modifier = Modifier.constrainAs(content) {
                    top.linkTo(btnBack.bottom, 16.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
                backgroundColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                ConstraintLayout(
                    modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    val (emailField) = createRefs()
                    OutlinedTextField(
                        value = email,
                        onValueChange = onTextChanged,
                        modifier = Modifier.constrainAs(emailField) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            width = Dimension.fillToConstraints
                            height = Dimension.wrapContent
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        label = {
                            Text(
                                modifier = Modifier,
                                text = "Email",
                                style = MaterialTheme.typography.body1,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    )
                }
            }
            Button(
                onClick = onSubmit,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.constrainAs(btnNext) {
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(45.dp)
                },

                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            ) {
                Text("Lanjutkan")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun UseEmailPreview() {
    ReminderObatTheme {
        Surface(color = MaterialTheme.colors.primaryVariant) {
            UseEmailView(uiState = UiState.Loading,
                onNavigateUp = { },
                onTextChanged = {},
                onSubmit = { /*TODO*/ },
                isDialogShow = false,
                dialogData = DialogData.init(),
                email = "test@email.com",
                setDialogData = {},
                onDialogDismiss = {},
                onResponseSuccess = {}
            )
        }
    }
}