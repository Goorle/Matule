package com.example.matule.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.matule.presentation.screens.Home
import com.example.matule.presentation.screens.Listing
import com.example.matule.presentation.screens.OnBoarding
import com.example.matule.presentation.screens.Popular
import com.example.matule.presentation.screens.SignIn
import com.example.matule.presentation.screens.SplashScreen

@Composable
fun NavigationApp(
    navHostController: NavHostController
) {

    NavHost(
        navController = navHostController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.SignIn.route) {
            SignIn(
                onClickSignIn = {
                    navHostController.navigate(Routes.Home.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(Routes.Home.route) {
            Home(
                navHostController = navHostController,
                onClickAllPopular = {
                    navHostController.navigate(Routes.Popular.route)
                },

            )
        }

        composable(Routes.SplashScreen.route) {
            SplashScreen {
                navHostController.navigate(Routes.OnBoarding.route) {
                    popUpTo(0)
                }
            }
        }

        composable(Routes.OnBoarding.route) {
            OnBoarding {
                navHostController.navigate(Routes.SignIn.route) {
                    popUpTo(0)
                }
            }
        }

        composable(Routes.Popular.route) {
            Popular() {
                navHostController.popBackStack()
            }
        }

        composable(Routes.Listing.route + "/{category}") { stackBackEntry ->
            val category = stackBackEntry.arguments?.getString("category")
            Listing(
                onClickBack = {
                    navHostController.popBackStack()
                },
                category = category ?: "Все"
            )
        }
    }
}