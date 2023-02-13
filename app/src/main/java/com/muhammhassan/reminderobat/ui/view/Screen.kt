package com.muhammhassan.reminderobat.ui.view

sealed class Screen(val route: String){
    object Splash: Screen("splash")
    object Home: Screen("home")
    object AddDrugs: Screen("add_drugs")
    object AddReminder: Screen("add_reminder")
    object AddStock: Screen("add_stock")
    object Detail: Screen("detail")
    object Progress: Screen("progress")
}
