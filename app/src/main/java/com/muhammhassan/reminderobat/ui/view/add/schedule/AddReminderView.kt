package com.muhammhassan.reminderobat.ui.view.add.schedule

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.muhammhassan.reminderobat.R
import com.muhammhassan.reminderobat.core.utils.Day
import com.muhammhassan.reminderobat.domain.model.DrugsData
import com.muhammhassan.reminderobat.ui.component.*
import org.koin.androidx.compose.koinViewModel
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddReminderView(
    modifier: Modifier = Modifier, onBackPressed:() -> Unit, onNextPressed: (data: DrugsData) -> Unit, data: DrugsData
) {
    val viewModel: AddReminderViewModel = koinViewModel()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val totalReminder by viewModel.totalReminder.collectAsState()
    val startDate by viewModel.startDate.collectAsState()
    val endDate by viewModel.endDate.collectAsState()
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
    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(scrollState)
                .weight(1f)
        ) {
            ButtonBack(modifier = Modifier) {
                onBackPressed.invoke()
            }
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Atur Waktu Pengingat",
                style = MaterialTheme.typography.h1
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "atur waktu untuk mengingatkanmu",
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier,
                text = "Masukan jumlah pengingat dalam satu hari",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    modifier = Modifier.size(45.dp),
                    onClick = { viewModel.decreaseReminder() },
                    enabled = (totalReminder > 0),
                    shape = CircleShape,
                    border = BorderStroke(2.dp, Color.Gray)
                ) {
                    Text(text = "-", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = totalReminder.toString()
                )
                OutlinedButton(
                    modifier = Modifier.size(45.dp),
                    onClick = { viewModel.increaseReminder() },
                    enabled = (totalReminder < 10),
                    shape = CircleShape,
                    border = BorderStroke(2.dp, Color.Gray),
                ) {
                    Text(text = "+", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier,
                text = "Pilih hari pengingat diaktifkan",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
            ) {
                DaySelector(
                    onItemSelected = { viewModel.setDay(Day.Sunday, it) },
                    text = "M",
                    baseColor = Color.Red
                )
                DaySelector(onItemSelected = { viewModel.setDay(Day.Monday, it) }, text = "S")
                DaySelector(onItemSelected = { viewModel.setDay(Day.Tuesday, it) }, text = "S")
                DaySelector(onItemSelected = { viewModel.setDay(Day.Wednesday, it) }, text = "R")
                DaySelector(onItemSelected = { viewModel.setDay(Day.Thursday, it) }, text = "K")
                DaySelector(onItemSelected = { viewModel.setDay(Day.Friday, it) }, text = "J")
                DaySelector(onItemSelected = { viewModel.setDay(Day.Saturday, it) }, text = "S")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Tanggal Mulai")
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(1.dp, Color.DarkGray)
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
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(modifier = Modifier.align(Alignment.End), text = "Tanggal Selesai")
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(1.dp, Color.DarkGray)
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
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (totalReminder > 0) {
                Text(
                    modifier = Modifier,
                    text = "Atur waktu awal pengingat",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                for (i in 1..totalReminder) {
                    TimeList(context = context, onTimeChanged = { time ->
                        viewModel.setHour(position = i - 1, time)
                    }, position = i)
                }
            } else {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Kamu belum mengatur total pengingat",
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

        }
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(45.dp), onClick = {
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
