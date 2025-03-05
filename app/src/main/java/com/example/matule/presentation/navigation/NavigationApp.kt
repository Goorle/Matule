package com.example.matule.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.matule.presentation.screens.SignInScreen

@Composable
fun NavigationApp(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.SignIn.route
    ) {
        composable(Routes.SignIn.route) {
            SignInScreen()
        }
    }
}