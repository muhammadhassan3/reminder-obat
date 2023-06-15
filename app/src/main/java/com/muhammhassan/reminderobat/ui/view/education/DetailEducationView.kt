package com.muhammhassan.reminderobat.ui.view.education

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.muhammhassan.reminderobat.domain.model.Articles
import com.muhammhassan.reminderobat.ui.component.ButtonBack
import com.muhammhassan.reminderobat.ui.component.DialogContent
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.utils.DialogData
import compose.icons.Octicons
import compose.icons.octicons.Image24
import compose.icons.octicons.X24

@Composable
fun DetailEducationView(
    onNavUp: () -> Unit,
    data: Articles?,
    isDialogShow: Boolean,
    dialogData: DialogData,
    onErrorResponse: (message: String) -> Unit,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(key1 = data, block = {
        if (data == null) {
            onErrorResponse.invoke("Gagal memuat data")
        }
    })

    if (isDialogShow) {
        Dialog(
            onDismissRequest = dialogData.onNeutralAction,
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            DialogContent(
                message = dialogData.message,
                title = dialogData.title,
                buttonType = dialogData.buttonType,
                onNeutralClicked = dialogData.onNeutralAction
            )
        }
    }
    data?.let {
        ConstraintLayout(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            val (btnNavUp, image, title, content) = createRefs()
            AsyncImage(
                model = data.image,
                contentDescription = data.title,
                error = rememberVectorPainter(
                    image = Octicons.X24
                ),
                modifier = Modifier.constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(250.dp)
                },
                contentScale = ContentScale.Fit,
                placeholder = rememberVectorPainter(image = Octicons.Image24)
            )

            ButtonBack(
                modifier = Modifier.constrainAs(btnNavUp) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                }, onClick = onNavUp
            )
            Text(text = data.title, modifier = Modifier.constrainAs(title) {
                top.linkTo(image.bottom, 8.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }, style = MaterialTheme.typography.h1)
            Text(text = data.content, modifier = Modifier.constrainAs(content) {
                top.linkTo(title.bottom, 8.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }, style = MaterialTheme.typography.body1)

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DetailEducationPreview() {
    ReminderObatTheme {
        DetailEducationView(
            onNavUp = {},
            data = Articles(1L, "Title", "Content", ""),
            modifier = Modifier.background(MaterialTheme.colors.primaryVariant),
            onErrorResponse = {},
            isDialogShow = false,
            dialogData = DialogData.init()
        )
    }
}