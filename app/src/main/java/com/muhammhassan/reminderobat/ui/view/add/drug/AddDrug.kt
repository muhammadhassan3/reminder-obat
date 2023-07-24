package com.muhammhassan.reminderobat.ui.view.add.drug

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muhammhassan.reminderobat.R
import com.muhammhassan.reminderobat.core.utils.Constant
import com.muhammhassan.reminderobat.domain.model.DrugsData
import com.muhammhassan.reminderobat.ui.component.ButtonBack
import com.muhammhassan.reminderobat.ui.component.InputField
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddDrugView(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onNextPressed: (data: DrugsData) -> Unit
) {
    val viewModel: AddDrugViewModel = koinViewModel()
    val title by viewModel.title.collectAsStateWithLifecycle()
    val weight by viewModel.weight.collectAsStateWithLifecycle()
    val type by viewModel.type.collectAsStateWithLifecycle()
    val afterEat by viewModel.afterEat.collectAsStateWithLifecycle()

    ConstraintLayout(modifier = modifier.fillMaxSize().padding(16.dp)) {
        val (btnNavUp, tvTitle, subtitle, content, btnNext) = createRefs()
        ButtonBack(modifier = Modifier.constrainAs(btnNavUp) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }) {
            onBackPressed.invoke()
        }
        Text(
            modifier = Modifier.constrainAs(tvTitle) {
                top.linkTo(btnNavUp.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, text = "Masukkan Obatmu", style = MaterialTheme.typography.h1
        )
        Text(
            modifier = Modifier.constrainAs(subtitle) {
                top.linkTo(tvTitle.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, text = "Jagalah kondisimu dengan minum obat", style = MaterialTheme.typography.body1
        )
        Card(modifier = Modifier.constrainAs(content) {
            top.linkTo(subtitle.bottom, 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }, shape = RoundedCornerShape(16.dp), backgroundColor = Color.White) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val (edtTitle, edtWeight, edtType, tagAfterEat, rbAfterEat) = createRefs()
                InputField(title = "Judul Obat",
                    onTextChanged = { viewModel.setTitle(it) },
                    value = title ?: "",
                    modifier = Modifier.constrainAs(edtTitle) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.preferredWrapContent
                    })
                InputField(title = "Dosis Obat",
                    onTextChanged = { viewModel.setWeight(it) },
                    value = weight ?: "",
                    modifier = Modifier.constrainAs(edtWeight) {
                        top.linkTo(edtTitle.bottom, 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.preferredWrapContent
                    })
                InputField(
                    title = "Jenis Obat",
                    onTextChanged = { viewModel.setType(it) },
                    value = type ?: "",
                    modifier = Modifier.constrainAs(edtType) {
                        top.linkTo(edtWeight.bottom, 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.preferredWrapContent
                    }
                )
                Text(
                    text = "Pilih waktu minum",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.constrainAs(tagAfterEat) {
                        top.linkTo(edtType.bottom, 8.dp)
                        start.linkTo(parent.start)
                    })

                Row(modifier = Modifier.constrainAs(rbAfterEat) {
                    top.linkTo(tagAfterEat.bottom, 4.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.preferredWrapContent
                }) {
                    Row(modifier = Modifier.weight(1f)) {
                        RadioButton(selected = afterEat == Constant.afterEat,
                            onClick = { viewModel.setAfterEat(Constant.afterEat) })
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = "Sesudah Makan"
                        )
                    }
                    Row(modifier = Modifier.weight(1f)) {
                        RadioButton(selected = afterEat == Constant.beforeEat,
                            onClick = { viewModel.setAfterEat(Constant.beforeEat) })
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = "Sebelum Makan"
                        )
                    }
                }
            }
        }
        Button(
            modifier = Modifier
                .constrainAs(btnNext) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(45.dp)
                },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
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