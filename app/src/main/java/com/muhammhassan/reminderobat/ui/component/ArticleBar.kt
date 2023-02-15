package com.muhammhassan.reminderobat.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.muhammhassan.reminderobat.domain.model.Articles
import com.muhammhassan.reminderobat.R

@Composable
fun Articles(modifier: Modifier = Modifier, list: SnapshotStateList<Articles>) {
    val stateList = rememberLazyListState()
    Column(modifier = modifier) {
        Text(text = "Articles", fontWeight = FontWeight.Bold)
        LazyRow(state = stateList, modifier = Modifier) {
            items(list, key = { it.id }) {
                AsyncImage(
                    modifier = Modifier.size(150.dp, 100.dp),
                    model = it.image,
                    contentDescription = "Article Header",
                    placeholder = painterResource(id = R.drawable.baseline_image_24)
                )
            }
        }
    }
}