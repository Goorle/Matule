package com.example.matule.presentation.navigation

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.matule.presentation.screens.DetailsScreen
import com.example.matule.presentation.screens.EditProfileScreen
import com.example.matule.presentation.screens.FavoriteScreen
import com.example.matule.presentation.screens.Home
import com.example.matule.presentation.screens.NotificationScreen
import com.example.matule.presentation.screens.RegisterScreen
import com.example.matule.presentation.screens.SignIn
import com.example.matule.presentation.screens.SplashScreen
import com.example.matule.presentation.screens.UserScreen


@Composable
fun NavigationApp(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.SplashScreen.route
    ) {

        composable(
            route = Routes.SignIn.route,
        ) {
            SignIn(
                onClickSignIn = {
                    navHostController.navigate(Routes.Home.route)
                },
                onClickRegister = {
                    navHostController.navigate(Routes.RegisterScreen.route)
                }
            )
        }

        composable(
            route = Routes.RegisterScreen.route
        ) {
            RegisterScreen(
                onClickBack = {
                    navHostController.popBackStack()
                },
                onSuccessfullyReg = {
                    navHostController.navigate(Routes.Home.route)
                }
            )
        }

        composable(
            route = Routes.SplashScreen.route
        ) {
            SplashScreen(onNavigateToLogin =  {
                    navHostController.navigate(Routes.SignIn.route) {
                        popUpTo(Routes.SplashScreen.route)
                }
            },
                onNavigateToMain = {
                    navHostController.navigate(Routes.Home.route) {
                        popUpTo(Routes.SplashScreen.route)
                    }
                })
        }

        composable(
            route = Routes.Home.route,
        ) {
            Home(navHostController = navHostController)
        }



        composable(
            route = Routes.Detail.route + "/{id}",
        ) { stackBackEntry ->
            val productId = stackBackEntry.arguments?.getString("id")
            DetailsScreen(
                itemId = productId,
                onClickBack = { navHostController.popBackStack() }
            )
        }

        composable(route = Routes.Favorite.route) {
            FavoriteScreen(
                navHostController = navHostController,
            )
        }

        composable(route = Routes.Profile.route) {
            UserScreen(
                onClickBack = {navHostController.popBackStack()},
                onClickEditProfile = {navHostController.navigate(Routes.EditProfileScreen.route)},
                navHostController = navHostController
            )
        }

        composable(route = Routes.EditProfileScreen.route) {
            EditProfileScreen(
                onClickBack = {navHostController.navigate(Routes.Profile.route) {launchSingleTop = true} }
            )
        }

        composable(route =  Routes.Notification.route) {
            NotificationScreen(
                navHostController = navHostController,
            )
        }
    }
}
