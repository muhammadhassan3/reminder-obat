package com.muhammhassan.reminderobat.ui.view.main

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muhammhassan.reminderobat.ui.view.Screen
import com.muhammhassan.reminderobat.ui.view.home.HomeView
import com.muhammhassan.reminderobat.ui.view.splash.SplashView

@Composable
fun MainView(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier.padding(16.dp),
        navController = navController,
        startDestination = Screen.Splash.route
    ){
        composable(Screen.Splash.route){
            SplashView(navController = navController)
        }
        
        composable(Screen.Home.route){
            HomeView(navController = navController)
        }
    }
}