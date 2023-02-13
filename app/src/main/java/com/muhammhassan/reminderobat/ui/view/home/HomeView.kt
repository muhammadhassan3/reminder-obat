package com.muhammhassan.reminderobat.ui.view.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.muhammhassan.reminderobat.ui.component.AlarmGroup
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.ui.view.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeView(modifier: Modifier = Modifier, navController: NavHostController) {
    val viewModel: HomeViewModel = koinViewModel()
    val date by viewModel.date
    val data = viewModel.data

    val scrollState = rememberLazyListState()
    val isMenuShown = remember { mutableStateOf(false) }
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Halo User",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
            Text(
                text = date,
                Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Alarm akan datang")
            LazyColumn(
                modifier = Modifier,
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(data, key = { it.id }) {
                    AlarmGroup(item = it)
                }
            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.BottomEnd)) {
            IconButton(onClick = { navController.navigate(Screen.AddDrugs.route) }) {
                
            }
        }
    }
}