package com.example.matule.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
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
import com.example.matule.presentation.components.ModalNavigationApp
import com.example.matule.presentation.ui.theme.Accent
import com.example.matule.presentation.ui.theme.Background
import com.example.matule.presentation.ui.theme.Block
import com.example.matule.presentation.ui.theme.Red
import com.example.matule.presentation.ui.theme.TextColor
import com.example.matule.presentation.viewmodel.FavoriteViewModel
import com.example.matule.presentation.viewmodel.SignInViewModel
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = viewModel(),
    navHostController: NavHostController
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationApp(drawerState = drawerState, navHostController = navHostController) {
        Scaffold(
            topBar = {
                TopFavoriteBar(
                    drawerState
                )
            },
            bottomBar = {
                BottomAppBar(navHostController)
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Background)
                    .padding(innerPadding)
            ) {

                if (viewModel.favoriteResponse.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp)
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
                } else if (!viewModel.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight().align(Alignment.Center),
                        contentAlignment = Alignment.Center
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize().padding(24.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.favorite_filled), // Ваша иконка
                                contentDescription = null,
                                tint = Red,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                text = "Ваша коллекция пуста",
                                fontFamily = poppins,
                                fontWeight = FontWeight.Normal,
                                fontSize = 18.sp,
                                color = TextColor,
                                textAlign = TextAlign.Center,
                                lineHeight = 30.sp
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Пополните вашу коллекцию нажатием на \"сердечко\" по публикации.",
                                fontFamily = poppins,
                                fontWeight = FontWeight.Normal,
                                fontSize = 18.sp,
                                color = TextColor,
                                textAlign = TextAlign.Center,
                                lineHeight = 30.sp
                            )
                        }
                    }
                }
            }
        }
    }

    if (viewModel.isVisibleMessage) {
        DialogError(viewModel)
    }
}

@Composable
fun DialogError(
    viewModel: FavoriteViewModel
) {
    AlertDialog(
        onDismissRequest = {
            viewModel.isVisibleMessage = false
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.isVisibleMessage = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Accent
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "OK",
                    fontSize = 16.sp
                )
            }
        },
        icon = {
            Icon(
                painter = painterResource(R.drawable.error),
                contentDescription = "Error",
                tint = Red,
                modifier = Modifier.size(48.dp)
            )
        },
        containerColor = Block,
        title = {
            Text(
                text = "Ошибка!",
                fontSize = 24.sp,
                color = TextColor,
                fontFamily = poppins
            )
        },
        text = {
            Text(
                text = viewModel.messageText,
                fontSize = 14.sp,
                color = TextColor,
                fontFamily = poppins
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopFavoriteBar(
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
    TopAppBar(
        title = {
            Text(
                text = "Коллекция",
                fontFamily = poppins,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    if (drawerState.isOpen) drawerState.close() else drawerState.open()
                }
            }) {
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
                enabled = false,
                modifier = Modifier.size(24.dp)
            ) {

            }

            Spacer(Modifier.width(5.dp))
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
    )
}