package com.example.matule.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matule.R
import com.example.matule.domain.font.poppins
import com.example.matule.presentation.ui.theme.Accent
import com.example.matule.presentation.ui.theme.Background
import com.example.matule.presentation.ui.theme.Block
import com.example.matule.presentation.ui.theme.Hint
import com.example.matule.presentation.ui.theme.Red
import com.example.matule.presentation.ui.theme.SubTextLight
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
                item {
                    Column {
                        Text(
                            text = "Nike Air Max 270 Essential",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextColor,
                            modifier = Modifier.fillMaxWidth(0.7f),
                            fontFamily = poppins,
                        )

                        Spacer(Modifier.height(5.dp))

                        Text(
                            text = "КАТЕГОРИЯ",
                            fontFamily = poppins,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Hint
                        )

                        Spacer(Modifier.height(5.dp))

                        Text(
                            text = "₽ ЦЕНА",
                            fontFamily = poppins,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextColor
                        )
                    }

                    Spacer(Modifier.height(25.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){
                        Image(
                            painter = painterResource(R.drawable.cap),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(0.7f),
                            contentScale = ContentScale.FillWidth,
                        )
                    }


                    Spacer(Modifier.height(20.dp))

                    LazyRow {
                        items(10) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Block)
                                    .padding(5.dp)
                            ) {
                                IconButton(
                                    onClick = {},
                                    modifier = Modifier.size(50.dp)
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.cap),
                                        contentDescription = null,
                                        contentScale = ContentScale.FillWidth,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                            Spacer(Modifier.width(10.dp))
                        }
                    }

                    Column {

                        Text(
                            text = "ОПИСАНИЕ",
                            fontFamily = poppins,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 24.sp,
                            color = Hint,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )

                        TextButton(
                            onClick = {},
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Accent
                            ),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Подробнее",
                                fontWeight = FontWeight.Normal,
                                fontFamily = poppins,
                                fontSize = 14.sp,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = {},
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = SubTextLight,
                                contentColor = TextColor
                            ),
                            modifier = Modifier.size(52.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.favorite_outlined),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(Modifier.width(15.dp))

                        Button(
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = Accent,
                                contentColor = Block
                            ),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.bag),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                )
                                Text(
                                    text = "В Корзину",

                                    )

                                Spacer(Modifier.size(24.dp))
                            }
                        }

                    }
                }
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