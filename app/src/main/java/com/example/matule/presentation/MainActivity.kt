package com.example.matule.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.matule.presentation.navigation.NavigationApp
import com.example.matule.presentation.ui.theme.MatuleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatuleTheme {
                MainScreen()
            }
        }
    }
}


@Composable
fun MainScreen() {
    NavigationApp(
        rememberNavController()
    )
}