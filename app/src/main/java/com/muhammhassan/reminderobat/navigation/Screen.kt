package com.muhammhassan.reminderobat.navigation

sealed class Screen(val route: String){
    object Home: Screen("home")
    object AddDrugs: Screen("add_drugs")
    object AddReminder: Screen("add_reminder/{${ArgsName.data}}")
    object AddStock: Screen("add_stock")
    object Detail: Screen("home/{${ArgsName.id}}"){
        fun createRoute(id: Int) = "home/$id"
    }
    object Progress: Screen("progress")
}

object ArgsName{
    const val data = "data"
    const val id = "id"
}

