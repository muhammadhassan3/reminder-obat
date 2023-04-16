package com.muhammhassan.reminderobat.ui.view.add.stock

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.muhammhassan.reminderobat.domain.model.DrugsData
import com.muhammhassan.reminderobat.ui.component.ButtonBack
import com.muhammhassan.reminderobat.ui.component.ButtonType
import com.muhammhassan.reminderobat.ui.component.DialogContent
import com.muhammhassan.reminderobat.ui.component.InputField
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddStockView(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onDataSaved: () -> Unit,
    data: DrugsData
) {
    val viewModel: AddStockViewModel = koinViewModel()
    val condition by viewModel.condition.collectAsState()
    val stock by viewModel.stock.collectAsState()
    val showDialog by viewModel.showDialog.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.setData(data)
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { viewModel.hideDialog() }, properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = true
            )
        ) {
            DialogContent(message = "Kemu belum mengisi semua form yang disediakan.",
                title = "Gagal Melanjutkan",
                buttonType = ButtonType.NEUTRAL,
                onNeutralClicked = {
                    viewModel.hideDialog()
                })
        }
    }
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (title, subtitle, content, btnNavUp, btnSave) = createRefs()

        ButtonBack(modifier = Modifier.constrainAs(btnNavUp) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }) {
            onBackPressed.invoke()
        }
        Text(
            modifier = Modifier.constrainAs(title) {
                top.linkTo(btnNavUp.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

            }, text = "Kondisi dan stok obat", style = MaterialTheme.typography.h1
        )
        Text(
            modifier = Modifier.constrainAs(subtitle) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, text = "atur kondisi dan stok obat", style = MaterialTheme.typography.body1
        )

        Card(modifier = Modifier.constrainAs(content) {
            top.linkTo(subtitle.bottom, 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.preferredWrapContent
        }, backgroundColor = Color.White, shape = RoundedCornerShape(16.dp)) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val (edtCondition, edtStock) = createRefs()
                InputField(title = "Kondisi tubuh", onTextChanged = {
                    viewModel.setCondition(it)
                }, value = condition ?: "", modifier = Modifier.constrainAs(edtCondition) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.preferredWrapContent
                })
                InputField(title = "Stok obat", onTextChanged = {
                    viewModel.setStock(it)
                }, value = stock.toString(), modifier = Modifier.constrainAs(edtStock) {
                    top.linkTo(edtCondition.bottom, 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.preferredWrapContent
                }, inputType = KeyboardType.Number)
            }
        }

        Button(modifier = Modifier.constrainAs(btnSave) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.value(45.dp)
            }, onClick = {
            viewModel.save(context = context) {
                onDataSaved.invoke()
                Toast.makeText(context, "Pengingat berhasil diatur", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Simpan")
        }
    }

}