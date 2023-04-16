package com.muhammhassan.reminderobat.ui.view.main

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.muhammhassan.reminderobat.domain.model.DrugsData
import com.muhammhassan.reminderobat.navigation.ArgsName
import com.muhammhassan.reminderobat.navigation.Screen
import com.muhammhassan.reminderobat.ui.view.add.drug.AddDrugView
import com.muhammhassan.reminderobat.ui.view.add.schedule.AddReminderView
import com.muhammhassan.reminderobat.ui.view.add.stock.AddStockView
import com.muhammhassan.reminderobat.ui.view.detail.history.DetailHistoryView
import com.muhammhassan.reminderobat.ui.view.detail.schedule.DetailScheduleView
import com.muhammhassan.reminderobat.ui.view.home.HomeView
import com.muhammhassan.reminderobat.ui.view.progress.ProgressView

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
            }, onProgressClicked = {
                navController.navigate(Screen.Progress.route)
            }, onItemClick = {id ->
                navController.navigate(Screen.DetailSchedule.createRoute(id))
            })

        }

        composable(
            route = Screen.DetailSchedule.route,
            arguments = listOf(
                navArgument(ArgsName.id) {
                    type = NavType.LongType
                }
            )
        ) {
            val id = it.arguments?.getLong(ArgsName.id) ?: 0
            DetailScheduleView(onNavigateUp = { navController.navigateUp() }, id = id)
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
                navController.navigate(Screen.AddStock.route) {}
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
        composable(Screen.Progress.route) {
            ProgressView(onNavigateUp = {
                navController.navigateUp()
            }, onItemClick = {
                navController.navigate(Screen.DetailProgress.createRoute(it))
            })
        }
        composable(route = Screen.DetailProgress.route,
            arguments = listOf(navArgument(ArgsName.id) {
                defaultValue = 0
                type = NavType.LongType
            })
        ) {
            val id = it.arguments?.getLong(ArgsName.id) ?: 0
            DetailHistoryView(onNavigateUp = {
                navController.navigateUp()
            }, drugsId = id)
        }
    }
}