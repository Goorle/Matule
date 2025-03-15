package com.example.matule.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matule.R
import com.example.matule.domain.font.poppins
import com.example.matule.presentation.ui.theme.Block
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    onNextScreen: () -> Unit
) {

    var visibility by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (true) {

                // Toggle visibility
                visibility = !visibility

                // Delay to control the toggle frequency
                delay(1000)

                // Adjust delay as needed
                onNextScreen()
            }
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(
                colors = listOf<Color>(
                    Color(0xFF48B2E7),
                    Color(0xFF0076B1)
                )
            )),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visibility,
            enter = fadeIn(animationSpec = tween(durationMillis = 800)),
            exit = fadeOut(animationSpec = tween(durationMillis = 800))
        ) {

            Row {
                Text(
                    text = stringResource(R.string.app_name).uppercase(),
                    fontFamily = poppins,
                    fontSize = 42.sp,
                    color = Block,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = "ME",
                    fontFamily = poppins,
                    fontSize = 24.sp,
                    color = Block,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(){}
}