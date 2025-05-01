package com.example.matule.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.matule.presentation.screens.DetailsScreen
import com.example.matule.presentation.screens.Home
import com.example.matule.presentation.screens.Listing
import com.example.matule.presentation.screens.OnBoarding
import com.example.matule.presentation.screens.Popular
import com.example.matule.presentation.screens.SignIn
import com.example.matule.presentation.screens.SplashScreen
import kotlinx.coroutines.delay


@Composable
fun NavigationApp(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.SplashScreen.route
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
                        // Без resetting всего стека
                        popUpTo(Routes.SplashScreen.route)
                }
            }
        }

        composable(
            route = Routes.Home.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessVeryLow
                        )
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessVeryLow
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
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessVeryLow
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
