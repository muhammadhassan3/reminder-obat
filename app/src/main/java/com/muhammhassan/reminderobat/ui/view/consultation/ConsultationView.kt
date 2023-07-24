package com.muhammhassan.reminderobat.ui.view.consultation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.muhammhassan.reminderobat.domain.model.ChatModel
import com.muhammhassan.reminderobat.domain.model.UiState
import com.muhammhassan.reminderobat.ui.component.ButtonBack
import com.muhammhassan.reminderobat.ui.component.DialogContent
import com.muhammhassan.reminderobat.ui.theme.Black40
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.utils.DialogData
import compose.icons.Octicons
import compose.icons.octicons.PaperAirplane24

@Composable
fun ConsultationView(
    uiState: UiState<String>,
    data: List<ChatModel>,
    onNavUp: () -> Unit,
    draft: String,
    onMessageChange: (message: String) -> Unit,
    userEmail: String,
    dialogData: DialogData,
    isDialogShow: Boolean,
    onSendClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()

    if (isDialogShow) {
        Dialog(onDismissRequest = dialogData.onNeutralAction) {
            DialogContent(
                message = dialogData.message,
                title = dialogData.title,
                buttonType = dialogData.buttonType,
                onNeutralClicked = dialogData.onNeutralAction
            )
        }
    }

    Surface(color = Color.White) {
        ConstraintLayout(modifier = modifier.fillMaxSize()) {
            val (btnBack, title, textField, btnSend, list) = createRefs()
            ButtonBack(onClick = onNavUp, modifier = Modifier.constrainAs(btnBack) {
                start.linkTo(parent.start, 16.dp)
                top.linkTo(parent.top, 16.dp)
            })
            Text(text = "Konsultasi", modifier = Modifier.constrainAs(title) {
                start.linkTo(btnBack.end, 8.dp)
                top.linkTo(btnBack.top)
                bottom.linkTo(btnBack.bottom)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
            }, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold)
            LazyColumn(
                modifier = Modifier.constrainAs(list) {
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(btnBack.bottom, 8.dp)
                    bottom.linkTo(textField.top)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }, state = scrollState, verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (i in data.indices) {
                    var senderShape = RoundedCornerShape(10.dp)
                    var receivedShape = RoundedCornerShape(10.dp)
                    if (i == 0 || data[i - 1].getDate() != data[i].getDate()) {
                        item(key = data[i].getDate()) {
                            DateMarker(date = data[i].getDate())
                        }
                    }
                    if (i == 0 || data[i - 1].sender != data[i].sender || data[i - 1].getDate() != data[i].getDate()) {
                        receivedShape = RoundedCornerShape(0.dp, 10.dp, 10.dp, 10.dp)
                        senderShape = RoundedCornerShape(10.dp, 0.dp, 10.dp, 10.dp)
                    }
                    if (data[i].sender == userEmail) {
                        item(key = data[i].id) {
                            SenderBubble(
                                time = data[i].getTime(), message = data[i].message, senderShape
                            )
                        }
                    } else {
                        item(key = data[i].id) {
                            ReceivedBubble(
                                time = data[i].getTime(),
                                message = data[i].message,
                                shape = receivedShape
                            )
                        }
                    }
                }
            }

            OutlinedTextField(
                value = draft,
                onValueChange = onMessageChange,
                modifier = Modifier.constrainAs(textField) {
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(btnSend.start, 8.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                    height = Dimension.wrapContent
                    width = Dimension.fillToConstraints
                },
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text("Ketikkan pesanmu disini") },
                enabled = uiState != UiState.Loading
            )
            IconButton(onClick = onSendClicked,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.primary)
                    .constrainAs(btnSend) {
                        end.linkTo(parent.end, 16.dp)
                        bottom.linkTo(parent.bottom, 16.dp)
                        top.linkTo(textField.top)
                    }, enabled = uiState !is UiState.Loading) {
                Crossfade(targetState = (uiState is UiState.Loading)) { isShow ->
                    if (isShow) {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        Icon(
                            imageVector = Octicons.PaperAirplane24,
                            contentDescription = "Kirim pesan",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SenderBubble(
    time: String, message: String, shape: RoundedCornerShape, modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (container, timeStamp) = createRefs()
        Text(text = time, modifier = Modifier.constrainAs(timeStamp) {
            end.linkTo(container.start, 8.dp)
            bottom.linkTo(parent.bottom)

        }, fontSize = 12.sp)
        Card(
            modifier = Modifier.constrainAs(container) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
            shape = shape,
            backgroundColor = Color.White,
            border = BorderStroke(1.dp, color = Color.LightGray)
        ) {
            Row(
                modifier.padding(16.dp)
            ) {
                Text(text = message)
            }
        }
    }
}

@Composable
fun ReceivedBubble(
    time: String, message: String, shape: RoundedCornerShape, modifier: Modifier = Modifier
) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (container, timeStamp) = createRefs()
        Card(
            modifier = Modifier.constrainAs(container) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            shape = shape,
            backgroundColor = MaterialTheme.colors.primaryVariant
        ) {
            Row(
                modifier.padding(16.dp)
            ) {
                Text(text = message, style = TextStyle(color = Color.White))
            }
        }
        Text(text = time, modifier = Modifier.constrainAs(timeStamp) {
            start.linkTo(container.end, 8.dp)
            bottom.linkTo(parent.bottom)
        }, fontSize = 12.sp)
    }
}

@Composable
fun DateMarker(date: String, modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (content) = createRefs()

        Card(modifier = Modifier.constrainAs(content) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }, backgroundColor = Black40) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = date, fontSize = 12.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DateMarkerPreview() {
    ReminderObatTheme {
        DateMarker(date = "12/12/2012")
    }
}

@Preview
@Composable
fun ReceivedBubblePreview() {
    ReminderObatTheme {
        ReceivedBubble(
            time = "12.45",
            message = "Hello guys!",
            shape = RoundedCornerShape(0.dp, 10.dp, 10.dp, 10.dp)
        )
    }
}

@Preview
@Composable
fun SenderBubblePreview() {
    ReminderObatTheme {
        SenderBubble(
            time = "10:45",
            message = "Hello World",
            shape = RoundedCornerShape(10.dp, 0.dp, 10.dp, 10.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ConsultationPreview() {
    ReminderObatTheme {
        Surface(color = MaterialTheme.colors.primaryVariant) {
            ConsultationView(
                onNavUp = {},
                onMessageChange = {},
                draft = "",
                userEmail = "test@email.com",
                dialogData = DialogData.init(),
                isDialogShow = false,
                onSendClicked = {},
                data = emptyList(),
                uiState = UiState.Success("")
            )
        }
    }
}