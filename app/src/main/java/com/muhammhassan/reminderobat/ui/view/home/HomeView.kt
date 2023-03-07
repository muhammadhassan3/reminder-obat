package com.muhammhassan.reminderobat.ui.view.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammhassan.reminderobat.ui.component.AlarmGroup
import com.muhammhassan.reminderobat.ui.component.Articles
import com.muhammhassan.reminderobat.ui.component.ButtonAddDrug
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeView(modifier: Modifier = Modifier, onAddPressed: () -> Unit) {
    val viewModel: HomeViewModel = koinViewModel()
    val date by viewModel.date.collectAsState()
    val data by viewModel.data.collectAsState()
    val articleData by viewModel.articleData.collectAsState()

    val scrollState = rememberLazyListState()

    LaunchedEffect(key1 = true, block = {
        viewModel.getData()
    })

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Halo, Selamat datang",
                style = MaterialTheme.typography.h1
            )
            Text(
                text = date,
                Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Alarm akan datang")
            if (data.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier,
                    state = scrollState,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(data, key = { it.id }) {
                        AlarmGroup(item = it)
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Kamu belum menambahkan pengingat",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            if (articleData.isNotEmpty()) Articles(list = articleData)
        }
        ButtonAddDrug(modifier = Modifier.align(Alignment.BottomEnd)) {
            onAddPressed.invoke()
        }
    }
}