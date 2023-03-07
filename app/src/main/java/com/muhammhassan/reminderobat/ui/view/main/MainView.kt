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
import com.muhammhassan.reminderobat.navigation.ArgsName
import com.muhammhassan.reminderobat.navigation.Screen
import com.muhammhassan.reminderobat.ui.view.add.drug.AddDrugView
import com.muhammhassan.reminderobat.ui.view.add.schedule.AddReminderView
import com.muhammhassan.reminderobat.ui.view.add.stock.AddStockView
import com.muhammhassan.reminderobat.ui.view.home.HomeView

@Composable
fun MainView(
    modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier.padding(16.dp),
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            HomeView(onAddPressed = {
                navController.navigate(Screen.AddDrugs.route)
//                navController.navigate(Screen.Detail.createRoute(1))
            })

        }

        composable(Screen.AddDrugs.route) {
            AddDrugView(onNextPressed = { data ->
                navController.currentBackStackEntry?.arguments?.putParcelable(
                    ArgsName.data, data
                )
                navController.navigate(Screen.AddReminder.route)
            }, onBackPressed = {
                navController.navigateUp()
            })
        }

        composable(Screen.AddReminder.route) {
            val data = navController.previousBackStackEntry?.arguments?.getParcelable(ArgsName.data)
                ?: DrugsData()
            AddReminderView(onBackPressed = {
                navController.navigateUp()
            }, onNextPressed = { finalData ->

                navController.currentBackStackEntry?.arguments?.putParcelable(
                    ArgsName.data, finalData
                )
                navController.navigate(Screen.AddStock.route) {
                }
            }, data = data)
        }

        composable(Screen.AddStock.route) {
            val data = navController.previousBackStackEntry?.arguments?.getParcelable(ArgsName.data)
                ?: DrugsData()
            AddStockView(onBackPressed = {
                navController.navigateUp()
            }, onDataSaved = {
                navController.popBackStack(Screen.Home.route, inclusive = false)
            }, data = data)
        }
    }
}