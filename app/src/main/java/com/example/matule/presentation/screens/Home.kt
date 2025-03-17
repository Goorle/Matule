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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.matule.R
import com.example.matule.domain.font.poppins
import com.example.matule.presentation.ui.theme.Accent
import com.example.matule.presentation.ui.theme.Background
import com.example.matule.presentation.ui.theme.Block
import com.example.matule.presentation.ui.theme.TextColor
import com.example.matule.presentation.viewmodel.HomeViewModel

@Composable
fun Home(
    viewModel: HomeViewModel = viewModel()
) {
    //viewModel.getCategories()
    Scaffold(
        topBar = {
            TopBar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Background)
                .padding(horizontal = 15.dp, vertical = 15.dp)
        ) {
            Categories(viewModel)
            Popularity()
        }
    }
}


@Composable
private fun Categories(
    viewModel: HomeViewModel
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
            val category = viewModel.categories
            if (category != null) {
                items(category) { item ->
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Block,
                            contentColor = TextColor
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(40.dp).width(100.dp)
                    ) {
                        Text(
                            text = item.name,
                            fontFamily = poppins,
                            fontSize = 12.sp,
                            color = TextColor,
                        )
                    }
                    Spacer(Modifier.width(15.dp))
                }
            } else {
                item {
                    CircularProgressIndicator(color = Accent)
                }
            }
        }
    }
}

@Composable
private fun Popularity() {
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

                }
            )
        }

        LazyRow {

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
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Background,

        )
    )
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    Home()
}