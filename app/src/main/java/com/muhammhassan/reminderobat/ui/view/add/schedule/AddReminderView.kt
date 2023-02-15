package com.muhammhassan.reminderobat.ui.view.add.schedule

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.muhammhassan.reminderobat.domain.model.DrugsData
import com.muhammhassan.reminderobat.R
import com.muhammhassan.reminderobat.ui.component.ButtonBack
import com.muhammhassan.reminderobat.ui.component.DaySelector
import com.muhammhassan.reminderobat.ui.component.TimeList
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.ui.view.ArgsName
import com.muhammhassan.reminderobat.ui.view.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddReminderView(
    modifier: Modifier = Modifier, navController: NavHostController, data: DrugsData
) {
    val viewModel: AddReminderViewModel = koinViewModel()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val totalReminder by viewModel.totalReminder

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        ButtonBack(modifier = Modifier) {
            navController.navigateUp()
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
            DaySelector(onItemSelected = {}, text = "M", baseColor = Color.Red)
            DaySelector(onItemSelected = {}, text = "S")
            DaySelector(onItemSelected = {}, text = "S")
            DaySelector(onItemSelected = {}, text = "R")
            DaySelector(onItemSelected = {}, text = "K")
            DaySelector(onItemSelected = {}, text = "J")
            DaySelector(onItemSelected = {}, text = "S")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Tanggal Mulai")
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(), border = BorderStroke(1.dp, Color.DarkGray)
                ) {
                    Column(modifier = Modifier) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "22 Januari 2022",
                            fontSize = 20.sp
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
                    modifier = Modifier.fillMaxWidth(), border = BorderStroke(1.dp, Color.DarkGray)
                ) {
                    Column(modifier = Modifier) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "22 Januari 2022",
                            fontSize = 20.sp
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
                    viewModel.setHour(position = i-1, time)
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
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(45.dp), onClick = {
            navController.currentBackStackEntry?.arguments?.putParcelable(ArgsName.data, viewModel.convertToData())
            navController.navigate(Screen.AddStock.route)
        }) {
            Text(text = stringResource(id = R.string.next))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AddReminderPreview() {
    ReminderObatTheme {
        AddReminderView(navController = rememberNavController(), data = DrugsData())
    }

}