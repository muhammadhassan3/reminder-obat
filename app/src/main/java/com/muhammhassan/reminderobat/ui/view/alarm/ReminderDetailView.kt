package com.muhammhassan.reminderobat.ui.view.alarm

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.muhammhassan.reminderobat.core.utils.Constant
import com.muhammhassan.reminderobat.ui.component.ButtonType
import com.muhammhassan.reminderobat.ui.component.DialogContent
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.ui.theme.Tosca
import com.muhammhassan.reminderobat.ui.theme.Tosca50p
import com.muhammhassan.reminderobat.utils.DialogData
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ReminderDetailView(modifier: Modifier = Modifier, onNavigateUp: () -> Unit, id: Int) {
    val viewModel: ReminderDetailViewModel = koinViewModel()
    val data by viewModel.data.collectAsState()
    val isDialogshow by viewModel.isDialogShow.collectAsState()

    val dialog = remember {
        mutableStateOf(DialogData(title = "Pemberitahuan",
            message = "Apakah anda yakin?",
            buttonType = ButtonType.YES_NO,
            onCancelAction = {
                viewModel.hideDialog()
            },
            onConfirmAction = {},
            onNeutralAction = {}))
    }

    LaunchedEffect(key1 = true, block = {
        if (id != 0) {
            viewModel.setData(id)
        } else {
            dialog.value = DialogData(
                title = "Pemberitahuan",
                message = "Item yang diberikan tidak valid",
                buttonType = ButtonType.NEUTRAL,
                onNeutralAction = onNavigateUp
            )
            viewModel.showDialog()
        }
    })

    if (isDialogshow) {
        Dialog(
            onDismissRequest = { viewModel.hideDialog() }, properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = true
            )
        ) {
            DialogContent(
                message = dialog.value.message,
                title = dialog.value.title,
                buttonType = dialog.value.buttonType,
                onConfirmClicked = dialog.value.onConfirmAction,
                onCancelClicked = dialog.value.onCancelAction,
                onNeutralClicked = dialog.value.onNeutralAction
            )
        }
    }

    Column(modifier = modifier) {
        data?.let {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = it.title,
                    style = MaterialTheme.typography.h1
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Untuk jam ${it.time}",
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(modifier = Modifier, text = "Berat Obat")
                    Text(modifier = Modifier, text = it.weight, color = Color.DarkGray)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(modifier = Modifier, text = "Jenis Obat")
                    Text(modifier = Modifier, text = it.type, color = Color.DarkGray)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(modifier = Modifier, text = "Waktu minum")
                    Text(
                        modifier = Modifier,
                        text = if (it.afterEat == Constant.afterEat) "Sesudah makan" else "Sebelum makan",
                        color = Color.DarkGray
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Divider(modifier = Modifier, thickness = 2.dp)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(modifier = Modifier, text = "Tgl mulai")
                    Text(modifier = Modifier, text = it.startDate, color = Color.DarkGray)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(modifier = Modifier, text = "Tgl selesai")
                    Text(modifier = Modifier, text = it.endDate, color = Color.DarkGray)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Divider(modifier = Modifier, thickness = 2.dp)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(modifier = Modifier, text = "Kondisi")
                    Text(modifier = Modifier, text = it.condition, color = Color.DarkGray)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(modifier = Modifier, text = "Stok obat")
                    Text(modifier = Modifier, text = it.stock.toString(), color = Color.DarkGray)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(modifier = Modifier) {
                    OutlinedButton(
                        modifier = Modifier.size(75.dp),
                        onClick = { onNavigateUp.invoke() },
                        shape = CircleShape,
                        border = BorderStroke(1.dp, Color.Gray)
                    ) {
                        Icon(
                            modifier = Modifier,
                            imageVector = Icons.Default.Close,
                            contentDescription = "Dismiss",
                            tint = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(modifier = Modifier.align(Alignment.CenterHorizontally), text = "Dismiss")
                }
                Column(modifier = Modifier) {
                    OutlinedButton(
                        modifier = Modifier
                            .size(75.dp)
                            .background(Tosca50p, CircleShape),
                        onClick = {
                            dialog.value = DialogData(
                                title = "Konfirmasi",
                                message = "Apakah anda sudah meminum obat ini?",
                                buttonType = ButtonType.YES_NO,
                                onCancelAction = viewModel::hideDialog,
                                onConfirmAction = { viewModel.confirm(it.id) { onNavigateUp.invoke() } }
                            )
                            viewModel.showDialog()
                        },
                        shape = CircleShape,
                        border = BorderStroke(1.dp, Tosca),
                    ) {
                        Icon(
                            modifier = Modifier,
                            imageVector = Icons.Default.Check,
                            contentDescription = "Confirm",
                            tint = Tosca
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(modifier = Modifier.align(Alignment.CenterHorizontally), text = "Confirm")
                }
                Column(modifier = Modifier) {
                    OutlinedButton(
                        modifier = Modifier.size(75.dp),
                        onClick = { viewModel.reschedule() },
                        shape = CircleShape,
                        border = BorderStroke(1.dp, Color.Gray)
                    ) {
                        Icon(
                            modifier = Modifier,
                            imageVector = Icons.Default.Close,
                            contentDescription = "Atur Ulang",
                            tint = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally), text = "Atur Ulang"
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ReminderDetailPreview() {
    ReminderObatTheme {
        ReminderDetailView(onNavigateUp = {}, id = 0)
    }
}