package com.muhammhassan.reminderobat.ui.view.add.schedule

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muhammhassan.reminderobat.R
import com.muhammhassan.reminderobat.domain.model.DrugsData
import com.muhammhassan.reminderobat.ui.component.ButtonBack
import com.muhammhassan.reminderobat.ui.component.ButtonType
import com.muhammhassan.reminderobat.ui.component.DaySelector
import com.muhammhassan.reminderobat.ui.component.DialogContent
import com.muhammhassan.reminderobat.ui.component.TimeList
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@Composable
fun AddReminderView(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onNextPressed: (data: DrugsData) -> Unit,
    data: DrugsData
) {
    val viewModel: AddReminderViewModel = koinViewModel()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val totalReminder by viewModel.totalReminder.collectAsStateWithLifecycle()
    val startDate by viewModel.startDate.collectAsStateWithLifecycle()
    val endDate by viewModel.endDate.collectAsStateWithLifecycle()
    val isDialogShow = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.setData(data)
    }
    if (isDialogShow.value) {
        Dialog(
            onDismissRequest = { isDialogShow.value = false }, properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = true
            )
        ) {
            DialogContent(message = "Kamu belum memasukkan total pengingat, silahkan ditambahkan terlebih dahulu",
                title = "Gagal Melanjutkan",
                buttonType = ButtonType.NEUTRAL,
                onNeutralClicked = { isDialogShow.value = false })
        }
    }

    ConstraintLayout(modifier = modifier.fillMaxSize().padding(16.dp)) {
        val (btnNavUp, btnNext, title, subtitle, content) = createRefs()
        ButtonBack(modifier = Modifier.constrainAs(btnNavUp) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }) {
            onBackPressed.invoke()
        }
        Text(
            modifier = Modifier.constrainAs(title) {
                top.linkTo(btnNavUp.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, text = "Atur Waktu Pengingat", style = MaterialTheme.typography.h1
        )
        Text(
            modifier = Modifier.constrainAs(subtitle) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, text = "atur waktu untuk mengingatkanmu", style = MaterialTheme.typography.body1
        )

        Card(modifier = Modifier.constrainAs(content) {
            top.linkTo(subtitle.bottom, 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(btnNext.top, 8.dp)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }, backgroundColor = Color.White, shape = RoundedCornerShape(16.dp)) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(scrollState
                    )
            ) {
                val (tagTotalReminder, btnMin, btnPlus, tvTotalReminder, tagDays, daysList, tagStartDate, tvStartDate, tagEndDate, tvEndDate, tagScheduleList, scheduleList) = createRefs()
                val guideline = createGuidelineFromStart(0.5f)
                Text(
                    modifier = Modifier.constrainAs(tagTotalReminder) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    text = "Masukan jumlah pengingat dalam satu hari",
                    fontWeight = FontWeight.Bold
                )
                OutlinedButton(modifier = Modifier.constrainAs(btnMin) {
                    top.linkTo(tagTotalReminder.bottom, 8.dp)
                    start.linkTo(parent.start)
                    width = Dimension.value(45.dp)
                    height = Dimension.value(45.dp)
                },
                    onClick = { viewModel.decreaseReminder() },
                    enabled = (totalReminder > 0),
                    shape = CircleShape,
                    border = BorderStroke(2.dp, Color.Gray)
                ) {
                    Text(text = "-", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
                Text(
                    modifier = Modifier.constrainAs(tvTotalReminder) {
                        top.linkTo(btnMin.top)
                        start.linkTo(btnMin.end)
                        end.linkTo(btnPlus.start)
                        bottom.linkTo(btnMin.bottom)
                    }, text = totalReminder.toString()
                )
                OutlinedButton(
                    modifier = Modifier.constrainAs(btnPlus) {
                        top.linkTo(tagTotalReminder.bottom, 4.dp)
                        end.linkTo(parent.end)
                        width = Dimension.value(45.dp)
                        height = Dimension.value(45.dp)
                    },
                    onClick = { viewModel.increaseReminder() },
                    enabled = (totalReminder < 10),
                    shape = CircleShape,
                    border = BorderStroke(2.dp, Color.Gray),
                ) {
                    Text(text = "+", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
                Text(
                    modifier = Modifier.constrainAs(tagDays) {
                        top.linkTo(btnMin.bottom, 8.dp)
                        start.linkTo(parent.start)
                    }, text = "Pilih hari pengingat diaktifkan", fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier.constrainAs(daysList) {
                        top.linkTo(tagDays.bottom, 4.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.preferredWrapContent
                    }, horizontalArrangement = Arrangement.SpaceAround
                ) {
                    DaySelector(
                        onItemSelected = { viewModel.setDay(Calendar.SUNDAY, it) },
                        text = "M",
                        baseColor = Color.Red
                    )
                    DaySelector(
                        onItemSelected = { viewModel.setDay(Calendar.MONDAY, it) }, text = "S"
                    )
                    DaySelector(
                        onItemSelected = { viewModel.setDay(Calendar.TUESDAY, it) }, text = "S"
                    )
                    DaySelector(
                        onItemSelected = { viewModel.setDay(Calendar.WEDNESDAY, it) }, text = "R"
                    )
                    DaySelector(
                        onItemSelected = { viewModel.setDay(Calendar.THURSDAY, it) }, text = "K"
                    )
                    DaySelector(
                        onItemSelected = { viewModel.setDay(Calendar.FRIDAY, it) }, text = "J"
                    )
                    DaySelector(
                        onItemSelected = { viewModel.setDay(Calendar.SATURDAY, it) }, text = "S"
                    )
                }
                Text(text = "Tanggal Mulai", modifier = Modifier.constrainAs(tagStartDate) {
                    top.linkTo(daysList.bottom, 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(guideline, 4.dp)
                })
                Card(
                    modifier = Modifier.constrainAs(tvStartDate) {
                        top.linkTo(tagStartDate.bottom, 4.dp)
                        start.linkTo(parent.start)
                        end.linkTo(guideline, 4.dp)
                        width = Dimension.fillToConstraints
                    }, border = BorderStroke(1.dp, Color.DarkGray)
                ) {
                    Column(modifier = Modifier) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .clickable {
                                    showDatePicker(context = context) { year, month, day ->
                                        viewModel.setStartDate(year, month, day)
                                    }
                                }, text = startDate, fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Text(modifier = Modifier.constrainAs(tagEndDate) {
                    start.linkTo(guideline, 4.dp)
                    end.linkTo(parent.end)
                    top.linkTo(daysList.bottom, 8.dp)
                }, text = "Tanggal Selesai")
                Card(
                    modifier = Modifier.constrainAs(tvEndDate) {
                        start.linkTo(guideline, 4.dp)
                        end.linkTo(parent.end)
                        top.linkTo(tagEndDate.bottom, 4.dp)
                        width = Dimension.fillToConstraints
                        height = Dimension.preferredWrapContent
                    }, border = BorderStroke(1.dp, Color.DarkGray)
                ) {
                    Column(modifier = Modifier) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .clickable {
                                    showDatePicker(context = context) { year, month, day ->
                                        viewModel.setEndDate(year, month, day)
                                    }
                                }, text = endDate, fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                if (totalReminder > 0) {
                    Text(
                        modifier = Modifier.constrainAs(tagScheduleList) {
                            top.linkTo(tvEndDate.bottom, 8.dp)
                            start.linkTo(parent.start)
                        }, text = "Atur waktu awal pengingat", fontWeight = FontWeight.Bold
                    )
                    Column(modifier = Modifier.constrainAs(scheduleList) {
                        top.linkTo(tagScheduleList.bottom, 4.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.preferredWrapContent
                    }) {

                        for (i in 1..totalReminder) {

                            TimeList(context = context, onTimeChanged = { time ->
                                viewModel.setHour(position = i - 1, time)
                            }, position = i)
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.constrainAs(scheduleList) {
                            top.linkTo(tvStartDate.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                        text = "Kamu belum mengatur total pengingat",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Button(modifier = Modifier.constrainAs(btnNext) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.value(45.dp)
        }, onClick = {
            if (totalReminder == 0) {
                isDialogShow.value = true
            } else {
                onNextPressed.invoke(viewModel.convertToData())
            }
        }) {
            Text(text = stringResource(id = R.string.next))
        }
    }

}

fun showDatePicker(context: Context, onDateSelected: (year: Int, month: Int, day: Int) -> Unit) {
    val calendar = Calendar.getInstance()
    val day = calendar[Calendar.DAY_OF_MONTH]
    val month = calendar[Calendar.MONTH]
    val year = calendar[Calendar.YEAR]
    val datePicker = DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
        onDateSelected.invoke(selectedYear, selectedMonth, selectedDay)
    }, year, month, day)

    datePicker.show()
}
