package com.muhammhassan.reminderobat.ui.view.alarm

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muhammhassan.reminderobat.core.utils.Constant
import com.muhammhassan.reminderobat.ui.component.ButtonType
import com.muhammhassan.reminderobat.ui.component.DialogContent
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.ui.theme.Tosca
import com.muhammhassan.reminderobat.ui.theme.Tosca50p
import com.muhammhassan.reminderobat.utils.DialogData
import org.koin.androidx.compose.koinViewModel

@Composable
fun ReminderDetailView(modifier: Modifier = Modifier, onFinished: () -> Unit, id: Int) {
    val viewModel: ReminderDetailViewModel = koinViewModel()
    val data by viewModel.data.collectAsStateWithLifecycle()
    val isDialogshow by viewModel.isDialogShow.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val dialog = remember {
        mutableStateOf(
            DialogData(title = "Pemberitahuan",
                message = "Apakah anda yakin?",
                buttonType = ButtonType.YES_NO,
                onCancelAction = {
                    viewModel.hideDialog()
                },
                onConfirmAction = {},
                onNeutralAction = {})
        )
    }

    LaunchedEffect(key1 = true, block = {
        if (id != 0) {
            viewModel.setData(id)
        } else {
            dialog.value = DialogData(
                title = "Pemberitahuan",
                message = "Item yang diberikan tidak valid",
                buttonType = ButtonType.NEUTRAL,
                onNeutralAction = onFinished
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
    ConstraintLayout(modifier = modifier.fillMaxSize().padding(16.dp)) {
        val (
            title, subtitle, content, btnDismiss, btnConfirm, btnAturUlang
        ) = createRefs()
        data?.let {
            Text(
                modifier = Modifier.constrainAs(title) {
                    linkTo(parent.start, parent.top, parent.end, parent.bottom, verticalBias = 0F)
                },
                text = it.title,
                style = MaterialTheme.typography.h1
            )
            Text(
                modifier = Modifier.constrainAs(subtitle) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                text = "Untuk jam ${it.time}",
                style = MaterialTheme.typography.body1
            )
            Card(
                modifier = Modifier.constrainAs(content) {
                    top.linkTo(subtitle.bottom, 16.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.preferredWrapContent
                }, backgroundColor = Color.White, shape = RoundedCornerShape(16.dp)
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    val (
                        tagWeight, weight, tagType, type, tagAfterEat, afterEat, divider1,
                        tagStartDate, startDate, tagEndDate, endDate, divider2
                    ) = createRefs()
                    val (
                        tagCondition, condition, tagStock, stock
                    ) = createRefs()
                    Text(modifier = Modifier.constrainAs(tagWeight) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }, text = "Dosis Obat")
                    Text(modifier = Modifier.constrainAs(weight) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }, text = it.weight, color = Color.DarkGray)
                    Text(modifier = Modifier.constrainAs(tagType) {
                        top.linkTo(tagWeight.bottom, 8.dp)
                        start.linkTo(parent.start)
                    }, text = "Jenis Obat")
                    Text(modifier = Modifier.constrainAs(type) {
                        top.linkTo(weight.bottom, 8.dp)
                        end.linkTo(parent.end)
                    }, text = it.type, color = Color.DarkGray)
                    Text(modifier = Modifier.constrainAs(tagAfterEat) {
                        top.linkTo(tagType.bottom, 8.dp)
                        start.linkTo(parent.start)
                    }, text = "Waktu minum")
                    Text(
                        modifier = Modifier.constrainAs(afterEat) {
                            top.linkTo(type.bottom, 8.dp)
                            end.linkTo(parent.end)
                        },
                        text = if (it.afterEat == Constant.afterEat) "Sesudah makan" else "Sebelum makan",
                        color = Color.DarkGray
                    )
                    Divider(modifier = Modifier.constrainAs(divider1) {
                        top.linkTo(tagAfterEat.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }, thickness = 2.dp)
                    Text(modifier = Modifier.constrainAs(tagStartDate) {
                        top.linkTo(divider1.bottom, 16.dp)
                        start.linkTo(parent.start)
                    }, text = "Tanggal mulai")
                    Text(modifier = Modifier.constrainAs(startDate) {
                        top.linkTo(divider1.bottom, 16.dp)
                        end.linkTo(parent.end)
                    }, text = it.startDate, color = Color.DarkGray)
                    Text(modifier = Modifier.constrainAs(tagEndDate) {
                        top.linkTo(tagStartDate.bottom, 8.dp)
                        start.linkTo(parent.start)
                    }, text = "Tanggal selesai")
                    Text(modifier = Modifier.constrainAs(endDate) {
                        top.linkTo(startDate.bottom, 8.dp)
                        end.linkTo(parent.end)
                    }, text = it.endDate, color = Color.DarkGray)
                    Divider(modifier = Modifier.constrainAs(divider2) {
                        top.linkTo(tagEndDate.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }, thickness = 2.dp)
                    Text(modifier = Modifier.constrainAs(tagCondition) {
                        top.linkTo(divider2.bottom, 16.dp)
                        start.linkTo(parent.start)
                    }, text = "Kondisi")
                    Text(modifier = Modifier.constrainAs(condition) {
                        top.linkTo(divider2.bottom, 16.dp)
                        end.linkTo(parent.end)
                    }, text = it.condition, color = Color.DarkGray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(modifier = Modifier.constrainAs(tagStock) {
                        top.linkTo(tagCondition.bottom, 8.dp)
                        start.linkTo(parent.start)
                    }, text = "Stok obat")
                    Text(modifier = Modifier.constrainAs(stock) {
                        top.linkTo(condition.bottom, 8.dp)
                        end.linkTo(parent.end)
                    }, text = it.stock.toString(), color = Color.DarkGray)

                }

            }

            //Confirm Button
            Column(modifier = Modifier.constrainAs(btnConfirm) {
                bottom.linkTo(parent.bottom, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.preferredWrapContent
                height = Dimension.preferredWrapContent
            }) {
                OutlinedButton(
                    modifier = Modifier
                        .size(75.dp)
                        .background(Tosca50p, CircleShape),
                    onClick = {
                        dialog.value = DialogData(title = "Konfirmasi",
                            message = "Apakah anda sudah meminum obat ini?",
                            buttonType = ButtonType.YES_NO,
                            onCancelAction = viewModel::hideDialog,
                            onConfirmAction = {
                                viewModel.confirm(it.id)
                                onFinished.invoke()
                            })
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
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Confirm"
                )
            }

            //Button Dismiss
            Column(modifier = Modifier.constrainAs(btnDismiss) {
                start.linkTo(parent.start)
                end.linkTo(btnConfirm.start)
                bottom.linkTo(parent.bottom, margin = 8.dp)
                width = Dimension.preferredWrapContent
                height = Dimension.preferredWrapContent
            }) {
                OutlinedButton(
                    modifier = Modifier.size(75.dp), onClick = {
                        viewModel.dismiss()
                        onFinished.invoke()
                    }, shape = CircleShape, border = BorderStroke(1.dp, Color.Gray)
                ) {
                    Icon(
                        modifier = Modifier,
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dismiss",
                        tint = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Dismiss"
                )
            }

            //Button reschedule
            Column(modifier = Modifier.constrainAs(btnAturUlang) {
                start.linkTo(btnConfirm.end)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, margin = 8.dp)
                width = Dimension.preferredWrapContent
                height = Dimension.preferredWrapContent
            }) {
                OutlinedButton(
                    modifier = Modifier.size(75.dp),
                    onClick = {
                        viewModel.reschedule(context, id)
                        Toast.makeText(
                            context,
                            "Kamu akan diingatkan kembali dalam 5 menit",
                            Toast.LENGTH_SHORT
                        ).show()

                        onFinished.invoke()
                    },
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
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Atur Ulang"
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ReminderDetailPreview() {
    ReminderObatTheme {
        ReminderDetailView(onFinished = {}, id = 0)
    }
}