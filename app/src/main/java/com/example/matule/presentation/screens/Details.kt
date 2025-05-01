package com.example.matule.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.matule.R
import com.example.matule.domain.font.poppins
import com.example.matule.domain.models.UserCollection
import com.example.matule.presentation.ui.theme.Accent
import com.example.matule.presentation.ui.theme.Background
import com.example.matule.presentation.ui.theme.Block
import com.example.matule.presentation.ui.theme.Hint
import com.example.matule.presentation.ui.theme.TextColor
import com.example.matule.presentation.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(
    itemId: String?,
    onClickBack: () -> Unit,
    viewModel: DetailsViewModel = viewModel()
) {
    if (itemId != null) {
        viewModel.getItem(itemId)
        viewModel.getUserCollection(itemId)
        viewModel.getImage()
    }

    Scaffold(
        topBar = {
            if (!viewModel.visiblePDF){
                TopBar(
                    onClickBack = onClickBack
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            if (!viewModel.visiblePDF) {
                Button(
                    onClick = {
                        viewModel.getUrlPDF()
                        viewModel.visiblePDF = true
                        if (itemId != null) {
                            viewModel.addPublicationToCollection(itemId)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Accent,
                        contentColor = Block,
                        disabledContainerColor = Accent.copy(alpha = 0.7f)
                    ),
                    modifier = Modifier.height(50.dp).fillMaxWidth(0.8f),
                    shape = RoundedCornerShape(15.dp),
                    enabled = viewModel.buttonEnable
                ) {
                    Text(
                        text = "Читать",
                        fontFamily = poppins,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    ) { innerPadding ->
        val currentItem = viewModel.item
        AnimatedVisibility(
            visible = currentItem != null,
            enter = scaleIn(),
            exit = scaleOut()
            ){
            if (viewModel.visiblePDF) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    PDFViewer(viewModel.urlPDF,
                        onClickBack,
                        viewModel.userCollection ?: viewModel.getEmptyCollection(itemId ?: "")
                        )
                }
            } else {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(Background)
                ) {
                    LazyColumn(
                        modifier = Modifier.padding(top = 5.dp, start = 15.dp, end = 15.dp)
                            .fillMaxWidth(),
                    ) {
                        val currentBitmap = viewModel.bitmap
                        if (currentBitmap != null && currentItem != null) {
                            item {
                                Text(
                                    text = currentItem.title,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    lineHeight = 26.sp,
                                    color = TextColor,
                                    fontFamily = poppins
                                )

                                Text(
                                    text = "Дата публикации: " + currentItem.publicationDate
                                        .split("-")
                                        .reversed()
                                        .joinToString("."),
                                    fontSize = 14.sp,
                                    fontFamily = poppins,
                                    color = Hint,
                                    fontWeight = FontWeight.Medium
                                )

                                Spacer(Modifier.height(15.dp))

                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        bitmap = currentBitmap.asImageBitmap(),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth(0.9f)
                                            .clip(RoundedCornerShape(15.dp)),
                                        contentScale = ContentScale.FillWidth,

                                        )
                                }

                                Spacer(modifier = Modifier.height(15.dp))

                                Text(
                                    text = "Описание",
                                    fontFamily = poppins,
                                    fontSize = 18.sp,
                                    color = TextColor,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Text(
                                    text = currentItem.description,
                                    fontSize = 14.sp,
                                    color = Hint,
                                    lineHeight = 24.sp,
                                    maxLines = viewModel.linesDescription,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.clickable {
                                        viewModel.changeLines()
                                    }
                                        .animateContentSize()
                                )

                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Text(
                                        text = "Подробнее",
                                        fontFamily = poppins,
                                        fontSize = 14.sp,
                                        color = Accent,
                                        modifier = Modifier.clickable {
                                            viewModel.changeLines()
                                        }
                                    )
                                }

                                Spacer(Modifier.height(100.dp))
                            }
                        } else {
                            item {
                                Box(modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator(color = Accent)
                                }
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
private fun TopBar(
    onClickBack: () -> Unit
) {
    TopAppBar(
        title = {

        },
        navigationIcon = {
            IconButton(
                onClick = onClickBack,
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
//    DetailsScreen(
//        onClickBack = {},
//
//    )
}