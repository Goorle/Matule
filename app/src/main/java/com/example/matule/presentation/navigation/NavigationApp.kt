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
import com.example.matule.presentation.screens.Home
import com.example.matule.presentation.screens.SignIn
import com.example.matule.presentation.screens.SplashScreen


@Composable
fun NavigationApp(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.Home.route
    ) {

        composable(
            route = Routes.SignIn.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                ) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(300)
                ) + fadeOut()
            }
        ) {
            SignIn(
                onClickSignIn = {
                    navHostController.navigate(Routes.Home.route)
                }
            )
        }

        composable(
            route = Routes.SplashScreen.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                ) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(300)
                ) + fadeOut()
            }
        ) {
            SplashScreen {
                    navHostController.navigate(Routes.SignIn.route) {
                        popUpTo(Routes.SplashScreen.route)
                }
            }
        }

        composable(
            route = Routes.Home.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(
                        delayMillis = 100,
                        easing = LinearOutSlowInEasing
                    )
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(
                        delayMillis = 100,
                        easing = FastOutLinearInEasing
                    )
                )
            }
        ) {
            Home(navHostController = navHostController)
        }



        composable(
            route = Routes.Detail.route + "/{id}",
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(
                        durationMillis = 100,
                        easing = FastOutLinearInEasing
                    )
                )
            }
        ) { stackBackEntry ->
            val productId = stackBackEntry.arguments?.getString("id")
            DetailsScreen(
                itemId = productId,
                onClickBack = { navHostController.popBackStack() }
            )
        }
    }
}
