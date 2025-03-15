package com.example.matule.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matule.R
import com.example.matule.domain.font.poppins
import com.example.matule.presentation.ui.theme.Block
import com.example.matule.presentation.ui.theme.Disable
import com.example.matule.presentation.ui.theme.SubTextLight
import com.example.matule.presentation.ui.theme.TextColor
import kotlinx.coroutines.launch

@Composable
fun OnBoarding(
    onNextScreen: () -> Unit
) {
    val pagerState = rememberPagerState { 3 }
    val coroutine = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        Color(0xFF48B2E7),
                        Color(0xFF0076B1)
                    )
                )
            )
            .padding(vertical = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        if (pagerState.currentPage == 0) {
            Text(
                text = stringResource(R.string.welcome).uppercase(),
                fontFamily = poppins,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Block,
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            HorizontalPager(
                state = pagerState
            ) {
                PagerItem(pagerState.currentPage)
            }
            Spacer(Modifier.height(50.dp))
            Row {
                repeat(3) {
                    val width = if (it == pagerState.currentPage) 50.dp else 30.dp
                    val color = if (it == pagerState.currentPage) Block else Disable
                    Box(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(12.dp))
                            .height(5.dp)
                            .width(width)
                            .background(color)
                    )
                    Spacer(Modifier.width(15.dp))
                }
            }
        }

        Button(
            onClick = {
                if (pagerState.currentPage == 2) {
                    onNextScreen()
                } else {
                    coroutine.launch {
                        pagerState.animateScrollToPage(
                            pagerState.currentPage + 1,
                        )
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Block,
                contentColor = TextColor
            ),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = if (pagerState.currentPage == 0) stringResource(R.string.start) else stringResource(R.string.next),
                color = TextColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun PagerItem(page: Int) {
    var textHeader = ""
    var text = ""
    val painter = when (page){
        0 -> {
            painterResource(R.drawable.image_1)
        }
        1 -> {
            textHeader = stringResource(R.string.let_start)
            text = stringResource(R.string.smart_collection)
            painterResource(R.drawable.image_2)
        }
        else -> {
            textHeader = stringResource(R.string.have_power)
            text = stringResource(R.string.your_room)
            painterResource(R.drawable.image_3)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painter,
            contentDescription = "Image",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = textHeader,
            fontFamily = poppins,
            color = Block,
            fontSize = 34.sp,
            textAlign = TextAlign.Center,
            lineHeight = 44.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = text,
            fontFamily = poppins,
            color = SubTextLight,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(0.8f)
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun OnBoardingPreview() {
    OnBoarding{}
}