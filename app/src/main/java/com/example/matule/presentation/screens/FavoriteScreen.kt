package com.example.matule.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.matule.R
import com.example.matule.domain.font.poppins
import com.example.matule.domain.models.CardData
import com.example.matule.presentation.components.BottomAppBar
import com.example.matule.presentation.components.CardProduct
import com.example.matule.presentation.ui.theme.Background
import com.example.matule.presentation.ui.theme.Red
import com.example.matule.presentation.ui.theme.TextColor
import com.example.matule.presentation.viewmodel.FavoriteViewModel

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = viewModel(),
    onClickBack: () -> Unit,
    navHostController: NavHostController
){
    Scaffold(
        topBar = {
            TopFavoriteBar(
                onClickBack = onClickBack
            )
        },
        bottomBar = {
            BottomAppBar(navHostController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(innerPadding)) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(viewModel.favoriteResponse) { item ->
                    val card = CardData(
                        publicationId = item.publicationId.id,
                        name = item.publicationId.title,
                        image = item.publicationId.image,
                        isFavorite = true,
                        publicationData = item.publicationId.publicationDate,
                    )

                    val currentCollection = item.userCollection

                    if (currentCollection != null) {
                        card.isReading = currentCollection.isReading
                        card.countPageReading = currentCollection.countReading
                    }

                    CardProduct(
                        card,
                        navHostController
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopFavoriteBar(
    onClickBack: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Коллекция",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onClickBack
            ) {
                Icon(
                    painter = painterResource(R.drawable.back_arrow),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Background,
            navigationIconContentColor = TextColor
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun FavoritePreview(){
    FavoriteScreen(
        navHostController =  rememberNavController(),
        onClickBack = {}
    )
}