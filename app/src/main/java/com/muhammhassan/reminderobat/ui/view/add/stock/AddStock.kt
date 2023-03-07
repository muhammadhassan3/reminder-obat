package com.muhammhassan.reminderobat.ui.view.add.stock

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.muhammhassan.reminderobat.domain.model.DrugsData
import com.muhammhassan.reminderobat.ui.component.ButtonBack
import com.muhammhassan.reminderobat.ui.component.ButtonType
import com.muhammhassan.reminderobat.ui.component.DialogContent
import com.muhammhassan.reminderobat.ui.component.InputField
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
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

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            ButtonBack(modifier = Modifier) {
                onBackPressed.invoke()
            }
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Kondisi dan stok obat",
                style = MaterialTheme.typography.h1
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "atur kondisi dan stok obat",
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(16.dp))
            InputField(title = "Kondisi tubuh", onTextChanged = {
                viewModel.setCondition(it)
            }, value = condition ?: "")
            Spacer(modifier = Modifier.height(8.dp))
            InputField(title = "Stok obat", onTextChanged = {
                viewModel.setStock(it)
            }, value = stock.toString(), inputType = KeyboardType.Number)
        }
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(45.dp), onClick = {
            viewModel.save(context = context) {
                onDataSaved.invoke()
            }
        }) {
            Text(text = "Simpan")
        }
    }

}