package com.example.matule.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matule.R
import com.example.matule.domain.font.poppins
import com.example.matule.presentation.ui.theme.Background
import com.example.matule.presentation.ui.theme.Block
import com.example.matule.presentation.ui.theme.TextColor

@Composable
fun DetailsScreen(

) {
    Scaffold(
        topBar = {
            TopBar()
        },
    ) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Background)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Shop",
                fontFamily = poppins,
                fontSize = 16.sp,
                color = TextColor,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {},
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Block
                ),
                modifier = Modifier.size(44.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.back_arrow),
                    contentDescription = null,
                    tint = TextColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = {},
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Block
                ),
                modifier = Modifier.size(44.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.bag),
                    contentDescription = null,
                    tint = TextColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Background
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    DetailsScreen()
}