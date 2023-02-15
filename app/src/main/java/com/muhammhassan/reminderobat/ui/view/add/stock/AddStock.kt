package com.muhammhassan.reminderobat.ui.view.add.stock

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.muhammhassan.reminderobat.ui.component.ButtonBack

@Composable
fun AddStockView(modifier: Modifier = Modifier, navController: NavHostController) {
    Column(modifier = modifier.fillMaxWidth()) {
        ButtonBack() {
            navController.navigateUp()
        }
    }
}