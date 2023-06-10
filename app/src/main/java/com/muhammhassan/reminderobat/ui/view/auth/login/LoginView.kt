package com.muhammhassan.reminderobat.ui.view.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.muhammhassan.reminderobat.ui.component.InputField
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme

@Composable
fun LoginView(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (title, content, btnLogin) = createRefs()
        Text(
            text = "Masuk", modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top, 20.dp)
                width = Dimension.preferredWrapContent
                height = Dimension.preferredWrapContent
            }, style = TextStyle(
                color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold
            )
        )
        Card(modifier = Modifier.constrainAs(content) {
            top.linkTo(title.bottom, 16.dp)
            start.linkTo(parent.start, 16.dp)
            end.linkTo(parent.end, 16.dp)
            width = Dimension.fillToConstraints
            height = Dimension.preferredWrapContent
        }, shape = RoundedCornerShape(16.dp)) {
            ConstraintLayout(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                val (edtEmail, edtPassword, tvResetPassword) = createRefs()
                InputField(title = "Email",
                    onTextChanged = {},
                    value = "",
                    modifier = Modifier.constrainAs(edtEmail) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
                    singleLine = true,
                    inputType = KeyboardType.Email
                )
                InputField(title = "Password",
                    onTextChanged = {},
                    value = "",
                    modifier = Modifier.constrainAs(edtPassword) {
                        top.linkTo(edtEmail.bottom, 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
                    singleLine = true,
                    inputType = KeyboardType.Password
                )
                Text(text = "Lupa Password?",
                    modifier
                        .constrainAs(tvResetPassword) {
                            top.linkTo(edtPassword.bottom, 16.dp)
                            end.linkTo(parent.end)
                        }
                        .clickable {
                            //Navigate to Reset Password
                        })
            }
        }
        Button(onClick = {}, modifier = Modifier.constrainAs(btnLogin) {
            start.linkTo(parent.start, 16.dp)
            end.linkTo(parent.end, 16.dp)
            bottom.linkTo(parent.bottom, 16.dp)
            width = Dimension.fillToConstraints
            height = Dimension.value(45.dp)
        }) {
            Text(text = "Masuk")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginPreview() {
    ReminderObatTheme {
        Surface(color = MaterialTheme.colors.background) {
            LoginView()
        }
    }
}