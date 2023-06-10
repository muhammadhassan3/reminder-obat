package com.muhammhassan.reminderobat.ui.view.auth.register

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
import com.muhammhassan.reminderobat.ui.component.ButtonBack
import com.muhammhassan.reminderobat.ui.component.InputField
import com.muhammhassan.reminderobat.ui.theme.ReminderObatTheme

@Composable
fun RegisterView(onNavUp : () -> Unit, modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (btnNavUp, title, content, btnRegister) = createRefs()
        ButtonBack(onClick = onNavUp, modifier = Modifier.constrainAs(btnNavUp){
            top.linkTo(parent.top, 16.dp)
            start.linkTo(parent.start, 16.dp)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        })
        Text(
            "Daftar", modifier = Modifier.constrainAs(title) {
                top.linkTo(btnNavUp.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.preferredWrapContent
                height = Dimension.preferredWrapContent
            }, style = TextStyle(
                color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold
            )
        )
        Card(modifier = Modifier.constrainAs(content){
            top.linkTo(title.bottom, 16.dp)
            start.linkTo(parent.start, 16.dp)
            end.linkTo(parent.end, 16.dp)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }, shape = RoundedCornerShape(16.dp)) {
            ConstraintLayout(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                val (edtEmail, edtPassword, edtConfirmPassword) = createRefs()
                InputField(title = "Email", onTextChanged = {}, value = "", modifier = Modifier.constrainAs(edtEmail){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, singleLine = true, inputType = KeyboardType.Email)
                InputField(title = "Password", onTextChanged = {}, value = "", modifier = Modifier.constrainAs(edtPassword){
                    top.linkTo(edtEmail.bottom, 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, singleLine = true, inputType = KeyboardType.Password)
                InputField(title = "Konfirmasi Password", onTextChanged = {}, value = "", modifier = Modifier.constrainAs(edtConfirmPassword){
                    top.linkTo(edtPassword.bottom, 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, singleLine = true, inputType = KeyboardType.Password)
            }
        }
        Button(onClick = { /*TODO*/ }, modifier = Modifier.constrainAs(btnRegister){
            start.linkTo(parent.start, 16.dp)
            end.linkTo(parent.end, 16.dp)
            bottom.linkTo(parent.bottom,16.dp)
            width = Dimension.fillToConstraints
            height = Dimension.value(45.dp)
        }) {
            Text(text = "Daftar")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun RegisterPreview() {
    ReminderObatTheme {
        Surface(color = MaterialTheme.colors.background) {
            RegisterView(onNavUp = {})
        }
    }
}