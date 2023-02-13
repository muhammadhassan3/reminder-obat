package com.muhammhassan.reminderobat.ui.view.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeView(modifier: Modifier = Modifier, navController: NavHostController? = null) {
    val viewModel: HomeViewModel = koinViewModel()
    val date by viewModel.date
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Halo User",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = date,
                Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomePreview() {
    ReminderObatTheme {
        HomeView()
    }
}