package com.muhammhassan.reminderobat.ui.view.add.drug

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muhammhassan.reminderobat.R
import com.muhammhassan.reminderobat.core.utils.Constant
import com.muhammhassan.reminderobat.domain.model.DrugsData
import com.muhammhassan.reminderobat.ui.component.ButtonBack
import com.muhammhassan.reminderobat.ui.component.InputField
import com.muhammhassan.reminderobat.ui.theme.Purple500
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddDrugView(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onNextPressed: (data: DrugsData) -> Unit
) {
    val viewModel: AddDrugViewModel = koinViewModel()
    val title by viewModel.title.collectAsState()
    val weight by viewModel.weight.collectAsState()
    val type by viewModel.type.collectAsState()
    val afterEat by viewModel.afterEat.collectAsState()

    Column(modifier = modifier.fillMaxWidth()) {
        ButtonBack(modifier = Modifier) {
            onBackPressed.invoke()
        }
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Masukkan Obatmu",
            style = MaterialTheme.typography.h1
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Jagalah kondisimu dengan minum obat",
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.height(16.dp))
        InputField(
            title = "Judul Obat", onTextChanged = { viewModel.setTitle(it) }, value = title ?: ""
        )
        Spacer(modifier = Modifier.height(4.dp))
        InputField(
            title = "Berat Obat", onTextChanged = { viewModel.setWeight(it) }, value = weight ?: ""
        )
        Spacer(modifier = Modifier.height(4.dp))
        InputField(
            title = "Jenis Obat", onTextChanged = { viewModel.setType(it) }, value = type ?: ""
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Pilih waktu minum", style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.weight(1f)) {
                RadioButton(
                    selected = afterEat == Constant.afterEat,
                    onClick = { viewModel.setAfterEat(Constant.afterEat) })
                Text(modifier = Modifier.align(Alignment.CenterVertically), text = "Sesudah Makan")
            }
            Row(modifier = Modifier.weight(1f)) {
                RadioButton(
                    selected = afterEat == Constant.beforeEat,
                    onClick = { viewModel.setAfterEat(Constant.beforeEat) })
                Text(modifier = Modifier.align(Alignment.CenterVertically), text = "Sebelum Makan")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Purple500),
            onClick = {
                onNextPressed.invoke(
                    viewModel.convertToData()
                )
            },
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text = stringResource(R.string.next))
        }
    }
}