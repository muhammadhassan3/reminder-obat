package com.muhammhassan.reminderobat.ui.view.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.muhammhassan.reminderobat.domain.model.GroupedDrugItem
import com.muhammhassan.reminderobat.ui.component.AlarmGroup
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import compose.icons.Octicons
import compose.icons.octicons.Book24
import compose.icons.octicons.CommentDiscussion24
import compose.icons.octicons.Person24

@Composable
fun HomeView(
    onItemClick: (id: Long) -> Unit,
    date: String,
    data: List<GroupedDrugItem>,
    onConsultationClicked: () -> Unit,
    onEducationClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    val scrollState = rememberLazyListState()

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (consultation, education, title, subTitle, alarmList) = createRefs()
        val dividerGuideline = createGuidelineFromStart(0.5f)
        Text(
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, text = "Halo, Selamat datang", style = MaterialTheme.typography.h1
        )
        Text(
            text = date, Modifier.constrainAs(subTitle) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, style = MaterialTheme.typography.body1
        )
        MenuItem(
            icon = Octicons.CommentDiscussion24,
            title = "Konsultasi",
            modifier.constrainAs(consultation) {
                start.linkTo(parent.start, 16.dp)
                end.linkTo(dividerGuideline, 8.dp)
                top.linkTo(subTitle.bottom, 16.dp)
                width = Dimension.fillToConstraints
            },
            onItemClick = onConsultationClicked
        )
        MenuItem(icon = Octicons.Book24, title = "Edukasi", modifier.constrainAs(education) {
            start.linkTo(dividerGuideline, 8.dp)
            end.linkTo(parent.end, 16.dp)
            top.linkTo(subTitle.bottom, 16.dp)
            width = Dimension.fillToConstraints
        }, onItemClick = onEducationClicked)
        Card(
            modifier = Modifier.constrainAs(alarmList) {
                top.linkTo(consultation.bottom, 8.dp)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            shape = RoundedCornerShape(
                bottomStart = 0.dp,
                bottomEnd = 0.dp,
                topEnd = 16.dp,
                topStart = 16.dp
            )
        ) {
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
                        },
                        state = scrollState,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        contentPadding = PaddingValues(16.dp)
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

    }
}

@Composable
fun MenuItem(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable {
            onItemClick.invoke()
        },
        backgroundColor = Color.White,
        border = BorderStroke(width = 1.dp, color = Color.Gray),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier
                    .size(65.dp)
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = title,
                modifier = Modifier,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview
@Composable
fun MenuItemPreview() {
    ReminderObatTheme {
        MenuItem(icon = Octicons.Person24, title = "About", onItemClick = {})
    }
}

@Preview(showSystemUi = true, apiLevel = 30, )
@Composable
fun HomePreview() {
    ReminderObatTheme {
        HomeView(
            modifier = Modifier.background(MaterialTheme.colors.primaryVariant),
            onItemClick = {},
            date = "2 April 2022",
            data = emptyList(),
            onConsultationClicked = {},
            onEducationClicked = {}
        )
    }
}