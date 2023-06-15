package com.muhammhassan.reminderobat.ui.view.education

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.muhammhassan.reminderobat.domain.model.Articles
import com.muhammhassan.reminderobat.domain.model.UiState
import com.muhammhassan.reminderobat.ui.component.ButtonBack
import com.muhammhassan.reminderobat.ui.component.DialogContent
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.utils.DialogData
import compose.icons.Octicons
import compose.icons.octicons.Image24
import compose.icons.octicons.X24

@Composable
fun EducationView(
    onNavUp: () -> Unit,
    data: UiState<List<Articles>>,
    onItemClicked: (data: Articles) -> Unit,
    onErrorResponse: (message: String) -> Unit,
    isDialogShow: Boolean,
    dialogData: DialogData,
    modifier: Modifier = Modifier
) {
    val isLoading = remember {
        mutableStateOf(false)
    }
    val contentData = remember {
        mutableStateListOf<Articles>()
    }

    LaunchedEffect(key1 = data, block = {
        when (data) {
            is UiState.Error -> {
                isLoading.value = false
                onErrorResponse.invoke(data.errorMessage)
            }

            UiState.Loading -> isLoading.value = true
            is UiState.Success -> {
                contentData.addAll(data.data)
                isLoading.value = false
            }
        }
    })

    if (isDialogShow) {
        Dialog(
            onDismissRequest = dialogData.onNeutralAction,
            properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false)
        ) {
            DialogContent(
                message = dialogData.message,
                title = dialogData.title,
                buttonType = dialogData.buttonType,
                onNeutralClicked = dialogData.onNeutralAction
            )
        }
    }

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (btnBack, title, content, loading) = createRefs()
        ButtonBack(onClick = onNavUp, modifier = Modifier.constrainAs(btnBack) {
            start.linkTo(parent.start, 16.dp)
            top.linkTo(parent.top, 16.dp)
        })
        Text(text = "Pusat Edukasi", modifier = Modifier.constrainAs(title) {
            start.linkTo(btnBack.end, 8.dp)
            top.linkTo(btnBack.top)
            bottom.linkTo(btnBack.bottom)
        }, fontSize = 18.sp)
        if (isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.constrainAs(loading) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            })
        } else {
            LazyColumn(modifier = Modifier.constrainAs(content) {
                top.linkTo(btnBack.bottom, 8.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                bottom.linkTo(parent.bottom, 16.dp)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }) {
                items(contentData, key = { it.id }) {
                    ArticlesItem(
                        item = it, onItemClicked = onItemClicked
                    )
                }
            }
        }
    }
}

@Composable
fun ArticlesItem(
    item: Articles, onItemClicked: (data: Articles) -> Unit, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onItemClicked.invoke(item) },
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color.White
    ) {
        ConstraintLayout(
            modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            val (image, tvTitle, tvSummary) = createRefs()
            AsyncImage(
                model = item.image, contentDescription = item.title, error = rememberVectorPainter(
                    image = Octicons.X24
                ), modifier = Modifier.constrainAs(image) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.value(85.dp)
                    height = Dimension.value(85.dp)
                }, placeholder = rememberVectorPainter(image = Octicons.Image24)
            )
            Text(text = item.title, modifier = Modifier.constrainAs(tvTitle) {
                start.linkTo(image.end, 8.dp)
                top.linkTo(image.top)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }, style = MaterialTheme.typography.h1, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(
                text = item.content,
                modifier = Modifier.constrainAs(tvSummary) {
                    top.linkTo(tvTitle.bottom, 4.dp)
                    start.linkTo(image.end, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                    bottom.linkTo(image.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
                style = MaterialTheme.typography.body1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}

@Preview
@Composable
fun ArticleItemPreview() {
    ReminderObatTheme {
        ArticlesItem(item = Articles(
            id = 0,
            title = "Title",
            content = "lajh fashdf asdfh lskdajf halsdiufhu sdkjf ahlsd fh asldja sjn daljfd asdljf alfjna slirufh",
            ""
        ), onItemClicked = {})
    }
}

@Preview(showSystemUi = true)
@Composable
fun EducationPreview() {
    ReminderObatTheme {
        EducationView(
            onNavUp = { /*TODO*/ },
            data = UiState.Success(
                listOf(Articles(1L, "Title", "Content", ""))
            ),
            onItemClicked = {},
            modifier = Modifier.background(MaterialTheme.colors.primaryVariant),
            onErrorResponse = {},
            isDialogShow = false,
            dialogData = DialogData.init()
        )
    }
}