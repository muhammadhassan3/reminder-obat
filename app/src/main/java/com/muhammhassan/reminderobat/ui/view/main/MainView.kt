package com.muhammhassan.reminderobat.ui.view.main

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muhammhassan.reminderobat.domain.model.DrugsData
import com.muhammhassan.reminderobat.ui.view.ArgsName
import com.muhammhassan.reminderobat.ui.view.Screen
import com.muhammhassan.reminderobat.ui.view.add.drug.AddDrugView
import com.muhammhassan.reminderobat.ui.view.add.schedule.AddReminderView
import com.muhammhassan.reminderobat.ui.view.home.HomeView

@Composable
fun MainView(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier.padding(16.dp),
        navController = navController,
        startDestination = Screen.Home.route
    ){
        
        composable(Screen.Home.route){
            HomeView(navController = navController)
        }

        composable(Screen.AddDrugs.route){
            AddDrugView(navController = navController)
        }

        composable(Screen.AddReminder.route){
            val data = navController.previousBackStackEntry?.arguments?.getParcelable(ArgsName.data) ?: DrugsData()
            AddReminderView(navController = navController, data = data)
        }

        composable(Screen.AddStock.route){
            val data = navController.previousBackStackEntry?.arguments?.getParcelable(ArgsName.data) ?: DrugsData()

        }
    }
}