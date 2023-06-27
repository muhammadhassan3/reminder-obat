package com.muhammhassan.reminderobat.ui.view.auth.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.muhammhassan.reminderobat.ui.component.DialogContent
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme
import com.muhammhassan.reminderobat.utils.DialogData
import compose.icons.Octicons
import compose.icons.octicons.ChevronRight24
import compose.icons.octicons.Person24

@Composable
fun ProfileView(
    userName: String,
    userEmail: String,
    onLogOutClick: () -> Unit,
    isDialogShow: Boolean,
    dialogData: DialogData,
    modifier: Modifier = Modifier
) {

    if (isDialogShow) {
        Dialog(onDismissRequest = dialogData.onCancelAction) {
            DialogContent(
                message = dialogData.message,
                title = dialogData.title,
                buttonType = dialogData.buttonType,
                onCancelClicked = dialogData.onCancelAction,
                onConfirmClicked = dialogData.onConfirmAction
            )
        }
    }

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (profile, logout, name, email) = createRefs()
        Icon(imageVector = Octicons.Person24,
            contentDescription = "Foto Profil",
            modifier = Modifier
                .constrainAs(profile) {
                    top.linkTo(parent.top, 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.value(90.dp)
                    height = Dimension.value(90.dp)
                }
                .clip(CircleShape)
                .background(Color.White),
            tint = Color.Black)
        Text(text = userName, modifier = Modifier.constrainAs(name) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(profile.bottom, 8.dp)
        }, fontSize = 22.sp)
        Text(text = userEmail, modifier = Modifier.constrainAs(email) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(name.bottom, 4.dp)
        })

        Card(modifier = Modifier
            .constrainAs(logout) {
                top.linkTo(email.bottom, 16.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
            }
            .clickable { onLogOutClick.invoke() },
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val (text, image) = createRefs()
                Text("Keluar", modifier = Modifier.constrainAs(text) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })
                Icon(
                    imageVector = Octicons.ChevronRight24,
                    contentDescription = "Keluar",
                    modifier = Modifier.constrainAs(image) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                    tint = Color.Black
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfilePreview() {
    ReminderObatTheme {
        Surface(color = MaterialTheme.colors.primaryVariant) {
            ProfileView(
                userName = "Name test",
                userEmail = "test@email.com",
                onLogOutClick = {},
                isDialogShow = false,
                dialogData = DialogData.init()
            )
        }
    }
}