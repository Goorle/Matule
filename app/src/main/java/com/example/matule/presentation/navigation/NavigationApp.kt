package com.example.matule.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.matule.presentation.screens.Home
import com.example.matule.presentation.screens.SignIn

@Composable
fun NavigationApp(
    navHostController: NavHostController
) {

    NavHost(
        navController = navHostController,
        startDestination = Routes.SignIn.route
    ) {
        composable(Routes.SignIn.route) {
            SignIn(
                onClickSignIn = {
                    navHostController.navigate(Routes.Home.route)
                }
            )
        }

        composable(Routes.Home.route) {
            Home()
        }
    }
}