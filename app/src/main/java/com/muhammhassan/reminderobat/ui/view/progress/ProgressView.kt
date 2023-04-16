package com.muhammhassan.reminderobat.ui.view.progress

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.muhammhassan.reminderobat.domain.model.HistoryListModel
import com.muhammhassan.reminderobat.ui.component.ButtonBack
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProgressView(
    modifier: Modifier = Modifier, onNavigateUp: () -> Unit, onItemClick: (id: Long) -> Unit
) {
    val viewModel: ProgressViewModel = koinViewModel()
    val data by viewModel.data.collectAsState(initial = emptyList())
    val scrollState = rememberLazyListState()

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (btnNavUp, title, subTitle, content) = createRefs()
        ButtonBack(modifier = Modifier.constrainAs(btnNavUp) {
            linkTo(
                parent.start,
                parent.top,
                parent.end,
                parent.bottom,
                horizontalBias = 0F,
                verticalBias = 0F
            )
        }) {
            onNavigateUp.invoke()
        }
        Text(
            modifier = Modifier.constrainAs(title) {
                top.linkTo(btnNavUp.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = "Progress",
            style = MaterialTheme.typography.h1,
        )
        Text(
            text = "Riwayat pengobatanmu", modifier = Modifier.constrainAs(subTitle) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, style = MaterialTheme.typography.body1
        )
        Card(modifier = Modifier.constrainAs(content) {
            top.linkTo(subTitle.bottom, margin = 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }, backgroundColor = Color.White, shape = RoundedCornerShape(16.dp)) {
            ConstraintLayout(modifier.fillMaxSize()) {
                val (list) = createRefs()
                if (data.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.constrainAs(list) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                                width = Dimension.fillToConstraints
                                height = Dimension.fillToConstraints
                            }, state = scrollState, verticalArrangement = Arrangement.spacedBy(4.dp), contentPadding = PaddingValues(16.dp)
                    ) {
                        items(data, key = { it.id }) {
                            ProgressItemView(data = it, onItemClick = onItemClick)
                        }
                    }
                } else {
                    Text(
                        text = "Data tidak tersedia",
                        modifier = Modifier
                            .constrainAs(list){
                                              top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            },
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}


@Composable
fun ProgressItemView(
    modifier: Modifier = Modifier, data: HistoryListModel, onItemClick: (id: Long) -> Unit
) {
    Card(modifier = Modifier.clickable {
        onItemClick.invoke(data.id)
    }, shape = RoundedCornerShape(8.dp), border = BorderStroke(1.dp, Color.Gray)) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = data.drugName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = data.status,
                    color = if (data.status == "Selesai") Color.Green else Color.Red
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = data.createdAt)
        }
    }
}