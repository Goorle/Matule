package com.example.matule.presentation.navigation

sealed class Routes(val route: String) {
    object SignIn: Routes("SignIn")
    object Home: Routes("Home")
    object SplashScreen: Routes("SplashScreen")
    object OnBoarding: Routes("OnBoarding")
}