package com.muhammhassan.reminderobat.ui.view.detail.schedule

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.muhammhassan.reminderobat.core.utils.Constant
import com.muhammhassan.reminderobat.ui.component.ButtonBack
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScheduleView(modifier: Modifier = Modifier, onNavigateUp: () -> Unit, id: Long) {
    val viewModel = koinViewModel<DetailScheduleViewModel>()
    val data by viewModel.getSchedule(id).collectAsState(initial = null)
    val context = LocalContext.current.applicationContext

    data?.let {
        ConstraintLayout(modifier = modifier.fillMaxSize().padding(16.dp)) {
            val (btnNavUp, btnDelete, title, content) = createRefs()
            ButtonBack(onClick = onNavigateUp, modifier = Modifier.constrainAs(btnNavUp) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            })
            IconButton(modifier = Modifier
                .size(55.dp)
                .constrainAs(btnDelete) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }, onClick = {
                onNavigateUp.invoke()
                viewModel.deleteSchedule(id, context)
            }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Hapus pengingat")
            }
            Text(
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(btnNavUp.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                text = it.name,
                style = MaterialTheme.typography.h1
            )
            Card(modifier = Modifier.constrainAs(content) {
                top.linkTo(title.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.preferredWrapContent
            }, shape = RoundedCornerShape(16.dp), backgroundColor = Color.White) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    val (tagWeight, weight, tagType, type, divider1, tagTime, time, tagAfterEat, afterEat, tagCondition, condition) = createRefs()
                    Text(text = "Dosis Obat", modifier = Modifier.constrainAs(tagWeight) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    })
                    Text(text = it.weight, modifier = Modifier.constrainAs(weight) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    })
                    Text(text = "Jenis obat", modifier = Modifier.constrainAs(tagType) {
                        top.linkTo(tagWeight.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                    })
                    Text(text = it.type, modifier = Modifier.constrainAs(type) {
                        top.linkTo(weight.bottom, margin = 8.dp)
                        end.linkTo(parent.end)
                    })
                    Text(text = "Waktu minum", modifier = Modifier.constrainAs(tagAfterEat) {
                        top.linkTo(tagType.bottom, 8.dp)
                        start.linkTo(parent.start)
                    })
                    Text(
                        text = if (it.afterEat == Constant.afterEat) "Sesudah makan" else "Sebelum makan",
                        modifier = Modifier.constrainAs(afterEat) {
                            top.linkTo(type.bottom, 8.dp)
                            end.linkTo(parent.end)
                        })
                    Divider(modifier = Modifier.constrainAs(divider1) {
                        top.linkTo(tagAfterEat.bottom, 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
                    Text(text = "Waktu pengingat", modifier = Modifier.constrainAs(tagTime) {
                        top.linkTo(divider1.bottom, 16.dp)
                        start.linkTo(parent.start)
                    })
                    Text(text = it.time, modifier = Modifier.constrainAs(time) {
                        top.linkTo(divider1.bottom, 16.dp)
                        end.linkTo(parent.end)
                    })
                    Text(text = "Kondisi", modifier = Modifier.constrainAs(tagCondition) {
                        top.linkTo(tagTime.bottom, 8.dp)
                        start.linkTo(parent.start)
                    })
                    Text(text = it.condition, modifier = Modifier.constrainAs(condition) {
                        top.linkTo(time.bottom, 8.dp)
                        end.linkTo(parent.end)
                    })
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun DetailSchedulePreview() {
    ReminderObatTheme {
        DetailScheduleView(onNavigateUp = {}, id = 0)
    }
}