package com.muhammhassan.reminderobat.ui.view.detail.history

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.muhammhassan.reminderobat.core.utils.Constant
import com.muhammhassan.reminderobat.core.utils.Utils
import com.muhammhassan.reminderobat.ui.component.ButtonBack
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailHistoryView(
    modifier: Modifier = Modifier, onNavigateUp: () -> Unit, drugsId: Long
) {
    val viewModel = koinViewModel<DetailHistoryViewModel>()
    val data by viewModel.getData(drugsId).collectAsState(initial = null)

    data?.let {
        val dayName = remember { mutableStateOf("") }

        LaunchedEffect(key1 = true, block = {
            dayName.value = Utils.parseDayName(it.day)
        })
        ConstraintLayout(modifier = modifier.fillMaxSize()) {
            val (btnNavUp, title, subtitle, content) = createRefs()

            ButtonBack(modifier = Modifier.constrainAs(btnNavUp) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }) {
                onNavigateUp.invoke()
            }
            Text(
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(btnNavUp.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, text = it.drugName, style = MaterialTheme.typography.h1
            )
            Text(modifier = Modifier.constrainAs(subtitle) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, text = it.confirmTime)

            Card(modifier = Modifier
                .constrainAs(content) {
                    top.linkTo(subtitle.bottom, 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.preferredWrapContent
                },
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color.White) {
                ConstraintLayout(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    val (tagWeight, weight, tagType, type, tagAfterEat, afterEat, divider1, tagCondition, condition, tagStock, stock, tagScheduledDay, scheduledDay) = createRefs()
                    val (divider2, tagScheduledTime, scheduledTime, tagStatus, status) = createRefs()
                    Text(text = "Berat Obat", modifier = Modifier.constrainAs(tagWeight) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    })
                    Text(text = it.drugsWeight, modifier = Modifier.constrainAs(weight) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    })
                    Text(text = "Jenis Obat", modifier = Modifier.constrainAs(tagType) {
                        top.linkTo(tagWeight.bottom, 8.dp)
                        start.linkTo(parent.start)
                    })
                    Text(text = it.drugsType, modifier = Modifier.constrainAs(type) {
                        top.linkTo(weight.bottom, 8.dp)
                        end.linkTo(parent.end)
                    })
                    Text(text = "Waktu Minum", modifier = Modifier.constrainAs(tagAfterEat) {
                        top.linkTo(tagType.bottom, 8.dp)
                        start.linkTo(parent.start)
                    })
                    Text(text = if (it.afterEat == Constant.afterEat) "Sesudah makan" else "Sebelum makan",
                        modifier = Modifier.constrainAs(afterEat) {
                            top.linkTo(type.bottom, 8.dp)
                            end.linkTo(parent.end)
                        })
                    Divider(modifier = Modifier.constrainAs(divider1) {
                        top.linkTo(tagAfterEat.bottom, 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    })
                    Text(text = "Kondisi", modifier = Modifier.constrainAs(tagCondition) {
                        top.linkTo(divider1.bottom, 16.dp)
                        start.linkTo(parent.start)
                    })
                    Text(text = it.condition, modifier = Modifier.constrainAs(condition) {
                        top.linkTo(divider1.bottom, 16.dp)
                        end.linkTo(parent.end)
                    })
                    Text(text = "Stok Obat", modifier = Modifier.constrainAs(tagStock) {
                        top.linkTo(tagCondition.bottom, 8.dp)
                        start.linkTo(parent.start)
                    })
                    Text(text = it.stock.toString(), modifier = Modifier.constrainAs(stock) {
                        top.linkTo(condition.bottom, 8.dp)
                        end.linkTo(parent.end)
                    })
                    Divider(modifier = Modifier.constrainAs(divider2) {
                        top.linkTo(tagStock.bottom, 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    })
                    Text(
                        text = "Dijadwalkan pada hari",
                        modifier = Modifier.constrainAs(tagScheduledDay) {
                            top.linkTo(divider2.bottom, 16.dp)
                            start.linkTo(parent.start)
                        })
                    Text(text = dayName.value, modifier = Modifier.constrainAs(scheduledDay) {
                        top.linkTo(divider2.bottom, 16.dp)
                        end.linkTo(parent.end)
                    })
                    Text(
                        text = "Dijadwalkan pada jam",
                        modifier = Modifier.constrainAs(tagScheduledTime) {
                            top.linkTo(tagScheduledDay.bottom, 8.dp)
                            start.linkTo(parent.start)
                        })
                    Text(text = it.time, modifier = Modifier.constrainAs(scheduledTime) {
                        top.linkTo(scheduledDay.bottom, 8.dp)
                        end.linkTo(parent.end)
                    })
                    Text(text = "Status", modifier = Modifier.constrainAs(tagStatus) {
                        top.linkTo(tagScheduledTime.bottom, 8.dp)
                        start.linkTo(parent.start)
                    })
                    Text(text = it.status, modifier = Modifier.constrainAs(status) {
                        top.linkTo(scheduledTime.bottom, 8.dp)
                        end.linkTo(parent.end)
                    })
                }
            }
        }
    }
}