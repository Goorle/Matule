package com.example.matule.presentation.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.matule.presentation.ui.theme.Block
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.matule.R
import com.example.matule.domain.font.poppins
import com.example.matule.domain.models.CardData
import com.example.matule.presentation.navigation.Routes
import com.example.matule.presentation.ui.theme.Accent
import com.example.matule.presentation.ui.theme.Hint
import com.example.matule.presentation.ui.theme.Red
import com.example.matule.presentation.ui.theme.TextColor
import com.example.matule.presentation.viewmodel.CardViewModel

@Composable
fun CardProduct(
    cardData: CardData,
    navHostController: NavHostController,
    viewModel: CardViewModel = viewModel(key = "card_${cardData.publicationId}"),
) {

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.getImage(cardData.image)
    }

    viewModel.checkFavorite(cardData.publicationId)

    Card(

        colors = CardDefaults.cardColors(
            containerColor = Block
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(160.dp),
        onClick = {
            navHostController.navigate(Routes.Detail.route + "/" + cardData.publicationId)
        }
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 10.dp,top = 10.dp, end = 10.dp)
            ) {
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier.height(200.dp).clip(RoundedCornerShape(7.dp)),

                ) {
                    val currentBitmap = viewModel.bitmap
                    if (currentBitmap != null) {
                        Image(
                            bitmap = currentBitmap.asImageBitmap(),
                            contentDescription = "Product",
                            modifier = Modifier.fillMaxWidth().background(Red),
                            contentScale = ContentScale.FillWidth

                        )
                        if (cardData.isReading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(TextColor.copy(alpha = 0.3f))
                            ) {
                                Row(
                                    modifier = Modifier.align(Alignment.BottomStart).padding(5.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Done,
                                        contentDescription = null,
                                        tint = Block,
                                        modifier = Modifier.size(24.dp)
                                    )

                                    Spacer(Modifier.width(2.dp))

                                    Text(
                                        text = "Прочитано",
                                        fontFamily = poppins,
                                        color = Block,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }

                        if (cardData.countPageReading > 0 && !cardData.isReading) {
                            Column(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 4.dp, end = 5.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            TextColor.copy(alpha = 0.4f),
                                            shape = RoundedCornerShape(7.dp)
                                        )
                                        .padding(vertical = 5.dp, horizontal = 10.dp)
                                ) {

                                    Text(
                                        text = "${cardData.countPageReading} / ${cardData.maxCountPage}",
                                        fontFamily = poppins,
                                        color = Block,
                                        fontSize = 16.sp,
                                    )
                                }
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Accent, modifier = Modifier.size(54.dp))
                        }
                    }
                    IconButton(
                        onClick = {
                            viewModel.updateFavorite(
                                viewModel.isFavorite,
                                cardData.publicationId
                            )
                        },
                        modifier = Modifier.size(32.dp).padding(start = 5.dp, top = 7.dp),

                    ) {
                        Icon(
                            painter = if (viewModel.isFavorite) painterResource(R.drawable.favorite_filled) else painterResource(R.drawable.favorite_outlined),
                            contentDescription = "favorite",
                            tint = if(viewModel.isFavorite) Red else Red,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                Spacer(Modifier.height(10.dp))
                Text(
                    text = cardData.publicationData
                        .split("-")
                        .reversed()
                        .joinToString("."),
                    fontFamily = poppins,
                    fontSize = 12.sp,
                    color = Accent,
                    fontWeight = FontWeight.Medium

                )
                Text(
                    text = cardData.name,
                    fontFamily = poppins,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Hint
                )

                Spacer(Modifier.height(5.dp))
            }
        }
    }

    if (viewModel.isVisibleMessage) {
        //Toast.makeText(context, viewModel.messageText, Toast.LENGTH_LONG).show()
    }
}




@Preview
@Composable
private fun CardPreview() {
    CardProduct(
        CardData(
            name = "Газета для вас",
        ),
        navHostController = rememberNavController(),
    )
}