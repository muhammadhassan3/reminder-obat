package com.muhammhassan.reminderobat.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammhassan.reminderobat.domain.model.DrugItem
import com.muhammhassan.reminderobat.domain.model.GroupedDrugItem
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme

@Composable
fun AlarmGroup(modifier: Modifier = Modifier, item: GroupedDrugItem, onItemClick: (id: Long) -> Unit) {
    val list = remember{ mutableStateListOf<DrugItem>() }
    LaunchedEffect(key1 = true){
        list.addAll(item.item)
    }
    Column(modifier = modifier) {
        Text(modifier = Modifier, text = item.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        item.item.forEach {
            DrugItemView(drugItem = it, onItemClick = onItemClick)
        }
    }
}

@Composable
fun DrugItemView(modifier: Modifier = Modifier, drugItem: DrugItem, onItemClick: (id: Long) -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
            .clickable { onItemClick.invoke(drugItem.id) },
        border = BorderStroke(1.dp, Color.Gray),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
            Row(modifier = Modifier){
                Text(text = drugItem.time, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "-")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = drugItem.title)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = drugItem.type)
        }
    }
}

@Preview
@Composable
fun DrugItemPreview() {
    ReminderObatTheme {
        DrugItemView(drugItem = DrugItem(title = "Obat 1", type = "1 Pill", time = "09:35", id=0), onItemClick = {})
    }
}

@Preview(showSystemUi = true)
@Composable
fun AlarmGroupPreview() {
    ReminderObatTheme {
        AlarmGroup(
            item = GroupedDrugItem(
                id = 0,
                title = "Hari ini", item = listOf(DrugItem(title = "Obat 1", type = "1 Pill", time = "08:45", id = 0))
            ),
            onItemClick = {}
        )
    }
}