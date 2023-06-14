package com.muhammhassan.reminderobat.navigation

sealed class Screen(val route: String){
    object Home: Screen("home")
    object AddDrugs: Screen("add_drugs")
    object AddReminder: Screen("add_reminder/{${ArgsName.data}}")
    object AddStock: Screen("add_stock")
    object DetailSchedule: Screen("home/{${ArgsName.id}}"){
        fun createRoute(id: Long) = "home/$id"
    }
    object DetailProgress: Screen("progress/{${ArgsName.id}}"){
        fun createRoute(id: Long) = "progress/$id"
    }
    object Progress: Screen("progress")
    object Register: Screen("register")
    object Login: Screen("login")
    object Education: Screen("education")
    object DetailEducation: Screen("education/${ArgsName.id}"){
        fun createRoute(id: Long) = "education/$id"
    }
}

object ArgsName{
    const val data = "data"
    const val id = "id"
}

