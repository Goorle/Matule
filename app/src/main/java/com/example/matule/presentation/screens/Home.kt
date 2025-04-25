package com.example.matule.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.matule.R
import com.example.matule.domain.font.poppins
import com.example.matule.presentation.components.BottomAppBar
import com.example.matule.presentation.ui.theme.Accent
import com.example.matule.presentation.ui.theme.Background
import com.example.matule.presentation.ui.theme.Block
import com.example.matule.presentation.ui.theme.TextColor
import com.example.matule.presentation.viewmodel.HomeViewModel

@Composable
fun Home(
    viewModel: HomeViewModel = viewModel(),
    onClickAllPopular: () -> Unit,
    navHostController: NavHostController
) {
    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomAppBar(navHostController)
        },
        floatingActionButton = {
            IconButton(
                onClick = {},
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Accent
                ),
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.bag),
                    contentDescription = null,
                    tint = Block,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Background)
                .padding(horizontal = 15.dp, vertical = 15.dp)
        ) {
            item {
                Categories(viewModel,
                    navHostController
                )
            }

            item {
                Spacer(Modifier.height(25.dp))
                Popularity(
                    viewModel,
                    onClickAllPopular
                )
            }


            item {
                Spacer(Modifier.height(60.dp))
            }
        }
    }
}


@Composable
private fun Categories(
    viewModel: HomeViewModel,
    navHostController: NavHostController
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

@Composable
private fun Popularity(
    viewModel: HomeViewModel,
    onClickAllPopular: () -> Unit
) {
    Column{
        Row(
          modifier = Modifier
              .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.popular),
                fontFamily = poppins,
                color = TextColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = stringResource(R.string.all),
                fontFamily = poppins,
                color = Accent,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable{
                    onClickAllPopular()
                }
            )
        }

        Spacer(Modifier.height(10.dp))
        LazyRow(

        ) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Icon(
                    painter = painterResource(R.drawable.highlight_05),
                    contentDescription = "Icon",
                    tint = TextColor,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = stringResource(R.string.explore),
                    fontFamily = poppins,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextColor,
                    textAlign = TextAlign.Center
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Image(
                    painter = painterResource(R.drawable.hamburger),
                    contentDescription = "Navigation Icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = {},
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Block,
                    contentColor = TextColor
                ),
                modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.bag),
                    contentDescription = "Bag",
                    tint = TextColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(Modifier.width(5.dp))
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Background,

        ),

    )
}


@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    Home(navHostController = rememberNavController(),
        onClickAllPopular = {})
}