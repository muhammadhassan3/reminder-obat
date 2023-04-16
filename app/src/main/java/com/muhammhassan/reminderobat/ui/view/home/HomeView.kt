package com.muhammhassan.reminderobat.ui.view.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.muhammhassan.reminderobat.ui.component.AlarmGroup
import com.muhammhassan.reminderobat.ui.component.Articles
import com.muhammhassan.reminderobat.ui.component.ButtonAddDrug
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    onAddPressed: () -> Unit,
    onProgressClicked: () -> Unit,
    onItemClick: (id: Long) -> Unit
) {
    val viewModel: HomeViewModel = koinViewModel()
    val date by viewModel.date.collectAsState()

    val data by viewModel.data.collectAsState()
    val articleData by viewModel.articleData.collectAsState()

    val scrollState = rememberLazyListState()

    LaunchedEffect(key1 = true, block = {
        viewModel.getData()
    })

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (menu, title, subTitle, alarmList) = createRefs()
        Text(
            modifier = Modifier.constrainAs(title) {
                linkTo(parent.start, parent.top, parent.end, parent.bottom, verticalBias = 0F)
            }, text = "Halo, Selamat datang", style = MaterialTheme.typography.h1
        )
        Text(
            text = date, Modifier.constrainAs(subTitle) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, style = MaterialTheme.typography.body1
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Card(modifier = Modifier.constrainAs(alarmList) {
            top.linkTo(subTitle.bottom, margin = 16.dp)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }, shape = RoundedCornerShape(16.dp)) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (content) = createRefs()

                if (data.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.constrainAs(content) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        }, state = scrollState, verticalArrangement = Arrangement.spacedBy(4.dp), contentPadding = PaddingValues(16.dp)
                    ) {
                        items(data, key = { it.id }) {
                            AlarmGroup(item = it, onItemClick = onItemClick)
                        }
                    }
                } else {
                    Text(
                        text = "Kamu belum menambahkan pengingat",
                        modifier = Modifier.constrainAs(content) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            },
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
        ButtonAddDrug(modifier = Modifier.constrainAs(menu) {
            linkTo(
                parent.start,
                parent.top,
                parent.end,
                parent.bottom,
                horizontalBias = 1F,
                verticalBias = 1F
            )
        }, onAddButtonClicked = {
            onAddPressed.invoke()
        }, onProgressButtonClicked = {
            onProgressClicked.invoke()
        })
    }
}