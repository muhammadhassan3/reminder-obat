package com.muhammhassan.reminderobat.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DetailSchedule : Screen("home/{${ArgsName.id}}") {
        fun createRoute(id: Long) = "home/$id"
    }

    object DetailProgress : Screen("progress/{${ArgsName.id}}") {
        fun createRoute(id: Long) = "progress/$id"
    }

    object Progress : Screen("progress")
    object Register : Screen("register")
    object Login : Screen("login")
    object Education : Screen("education")
    object Consultation : Screen("consultation")
    object Profile : Screen("profile")
}

object ArgsName {
    const val data = "data"
    const val id = "id"
    const val title = "title"
    const val content = "content"
    const val token = "token"
}

