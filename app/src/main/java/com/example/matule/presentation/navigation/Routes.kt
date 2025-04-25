package com.example.matule.presentation.navigation

sealed class Routes(val route: String) {
    object SignIn: Routes("SignIn")
    object Home: Routes("Home")
    object SplashScreen: Routes("SplashScreen")
    object OnBoarding: Routes("OnBoarding")
    object Favorite: Routes("Favorite")
    object Notification: Routes("Notification")
    object Profile: Routes("Profile")
    object Popular: Routes("Popular")
    object Listing: Routes("Listing")
    object Detail: Routes("Detail")
}