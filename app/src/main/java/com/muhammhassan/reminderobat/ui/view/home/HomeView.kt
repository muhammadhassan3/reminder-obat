package com.muhammhassan.reminderobat.ui.view.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.muhammhassan.reminderobat.ui.component.AlarmGroup
import com.muhammhassan.reminderobat.ui.component.Articles
import com.muhammhassan.reminderobat.ui.component.ButtonAddDrug
import com.muhammhassan.reminderobat.ui.view.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeView(modifier: Modifier = Modifier, navController: NavHostController) {
    val viewModel: HomeViewModel = koinViewModel()
    val date by viewModel.date
    val data = viewModel.data
    val articleData = viewModel.articleData

    val scrollState = rememberLazyListState()
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Halo User",
                style = MaterialTheme.typography.h1
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
            if (articleData.size > 0) Articles(list = articleData)
        }
        ButtonAddDrug(modifier = Modifier.align(Alignment.BottomEnd)) {
            navController.navigate(Screen.AddDrugs.route)
        }
    }
}