package com.muhammhassan.reminderobat.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.muhammhassan.remainderobat.domain.model.DrugItem
import com.muhammhassan.remainderobat.domain.model.GroupedDrugItem
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme

@Composable
fun AlarmGroup(modifier: Modifier = Modifier, item: GroupedDrugItem) {
    val list = remember{ mutableStateListOf<DrugItem>() }
    LaunchedEffect(key1 = true){
        list.addAll(item.item)
    }
    Column(modifier = modifier) {
        Text(modifier = Modifier, text = item.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        item.item.forEach {
            DrugItemView(drugItem = it)
        }
    }
}

@Composable
fun DrugItemView(modifier: Modifier = Modifier, drugItem: DrugItem) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        border = BorderStroke(1.dp, Color.Gray),
        backgroundColor = MaterialTheme.colors.background,
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = drugItem.title)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = drugItem.type)
        }
    }
}

@Preview
@Composable
fun DrugItemPreview() {
    ReminderObatTheme {
        DrugItemView(drugItem = DrugItem(title = "Obat 1", type = "1 Pill"))
    }
}

@Preview(showSystemUi = true)
@Composable
fun AlarmGroupPreview() {
    ReminderObatTheme {
        AlarmGroup(
            item = GroupedDrugItem(
                id = 0,
                title = "08:00", item = listOf(DrugItem(title = "Obat 1", type = "1 Pill"))
            )
        )
    }
}