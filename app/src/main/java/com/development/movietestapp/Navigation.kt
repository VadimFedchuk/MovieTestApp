package com.development.movietestapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.development.movietestapp.ui.screens.HomeScreen
import com.development.movietestapp.ui.screens.LoginScreen

@Composable
fun MovieAppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationRoute.LOGIN.route) {
        composable(NavigationRoute.LOGIN.route) {
            LoginScreen(navController)
        }
        composable(NavigationRoute.HOME.route) {
            HomeScreen(navController)
        }
    }
}

enum class NavigationRoute (val route: String) {
    LOGIN("login"), HOME("home")
}