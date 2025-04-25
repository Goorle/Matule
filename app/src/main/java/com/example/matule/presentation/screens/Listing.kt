package com.example.matule.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.matule.R
import com.example.matule.domain.font.poppins
import com.example.matule.presentation.components.CardProduct
import com.example.matule.presentation.ui.theme.Accent
import com.example.matule.presentation.ui.theme.Background
import com.example.matule.presentation.ui.theme.Block
import com.example.matule.presentation.ui.theme.TextColor
import com.example.matule.presentation.viewmodel.ListingViewModel

@Composable
fun Listing(
    viewModel: ListingViewModel = viewModel(),
    onClickBack: () -> Unit,
    category: String
) {
    Scaffold(
        topBar = {
            TopBar(
                onClickBack = onClickBack,
                viewModel = viewModel
            )
        },
        containerColor = Background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Background)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 15.dp)
            ) {
                Categories(
                    viewModel
                )

                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxSize()
                        .background(Background),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {


                }
            }
        }
    }
}

@Composable
private fun Categories(
    viewModel: ListingViewModel
) {
    Column {
        Text(
            text = stringResource(R.string.category),
            fontFamily = poppins,
            color = TextColor,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
        Spacer(Modifier.height(10.dp))
        LazyRow {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    viewModel: ListingViewModel,
    onClickBack: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = viewModel.currentCategory,
                fontFamily = poppins,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = TextColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onClickBack()
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Block
                ),
                modifier = Modifier.size(44.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.back_arrow),
                    contentDescription = "Back",
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
                    painter = painterResource(R.drawable.favorite_outlined),
                    contentDescription = "Favorite",
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