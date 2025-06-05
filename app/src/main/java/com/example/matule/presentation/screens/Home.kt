package com.example.matule.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.matule.R
import com.example.matule.domain.font.poppins
import com.example.matule.domain.models.CardData
import com.example.matule.presentation.components.BottomAppBar
import com.example.matule.presentation.components.CardProduct
import com.example.matule.presentation.navigation.Routes
import com.example.matule.presentation.ui.theme.Accent
import com.example.matule.presentation.ui.theme.Background
import com.example.matule.presentation.ui.theme.Block
import com.example.matule.presentation.ui.theme.TextColor
import com.example.matule.presentation.viewmodel.HomeViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home(
    viewModel: HomeViewModel = viewModel(),
    navHostController: NavHostController
) {

    LaunchedEffect(Unit) {
        navHostController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.route == Routes.Home.route) {
                viewModel.updateNewsPaper()
            }
        }
    }

    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomAppBar(navHostController)
        },
    ) { innerPadding ->
        LazyColumn (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Background)
                .padding(horizontal = 15.dp, vertical = 15.dp)
        ) {
            item {
                Box(
                    modifier = Modifier.height(1200.dp)
                ) {
                    NewspaperRow(
                        viewModel,
                        navHostController
                        )
                }
            }
        }
    }
}

@Composable
private fun NewspaperRow(
    viewModel: HomeViewModel,
    navHostController: NavHostController
    ) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Газеты",
                fontSize = 24.sp,
                fontFamily = poppins,
                lineHeight = 24.sp,
                color = TextColor,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "Все",
                fontSize = 16.sp,
                fontFamily = poppins,
                lineHeight = 24.sp,
                color = Accent,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable{

                }
            )
        }

        Spacer(Modifier.height(15.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),

        ) {
            items(
                viewModel.getNewsPaper()
            ) { item ->
                val card = CardData(
                    publicationId = item.publication?.id ?: "",
                    name = item.publication?.title ?: "",
                    image = item.publication?.image ?: "",
                    publicationData = item.publication?.publicationDate ?: ""
                )

                val currentCollection = item.userCollection

                if (currentCollection != null) {
                    card.isReading = currentCollection.isReading
                    card.countPageReading = currentCollection.countReading
                }

                CardProduct(
                    cardData = card,
                    navHostController
                )
            }
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
      )
}