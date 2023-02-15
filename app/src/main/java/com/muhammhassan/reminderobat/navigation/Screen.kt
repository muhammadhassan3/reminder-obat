package com.muhammhassan.reminderobat.ui.view

import com.muhammhassan.reminderobat.domain.model.DrugsData

sealed class Screen(val route: String){
    object Splash: Screen("splash")
    object Home: Screen("home")
    object AddDrugs: Screen("add_drugs")
    object AddReminder: Screen("add_reminder/{${ArgsName.data}}"){
        fun createRoute(data: DrugsData) = "add_reminder/$data"
    }
    object AddStock: Screen("add_stock")
    object Detail: Screen("detail")
    object Progress: Screen("progress")
}

object ArgsName{
    const val data = "data"
}
