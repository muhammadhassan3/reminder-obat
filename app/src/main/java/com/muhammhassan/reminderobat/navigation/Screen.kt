package com.muhammhassan.reminderobat.navigation

import android.net.Uri
import com.google.gson.Gson
import com.muhammhassan.reminderobat.domain.model.DrugsData

sealed class Screen(val route: String){
    object Home: Screen("home")
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
    object DetailEducation: Screen("education/{${ArgsName.id}}?title={${ArgsName.title}}&image={${ArgsName.imageUrl}}&content={${ArgsName.content}}"){
        fun createRoute(id: String, title: String, image: String?, content: String) = "education/${id}?title=${title}&image=${image}&content=${content}"
    }
    object Consultation: Screen("consultation")
    object Profile:Screen("profile")
}

object ArgsName{
    const val data = "data"
    const val id = "id"
    const val title = "title"
    const val imageUrl = "image"
    const val content = "content"
    const val token = "token"
}

