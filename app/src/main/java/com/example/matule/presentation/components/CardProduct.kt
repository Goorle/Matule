package com.example.matule.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.matule.presentation.ui.theme.Block
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.matule.R
import com.example.matule.domain.font.poppins
import com.example.matule.domain.models.CardData
import com.example.matule.domain.models.Publication
import com.example.matule.presentation.ui.theme.Accent
import com.example.matule.presentation.ui.theme.Background
import com.example.matule.presentation.ui.theme.Hint
import com.example.matule.presentation.ui.theme.Red
import com.example.matule.presentation.ui.theme.TextColor
import com.example.matule.presentation.viewmodel.CardViewModel

@Composable
fun CardProduct(
    cardData: CardData,
    viewModel: CardViewModel = viewModel(key = "card_${cardData.publicationId}")
) {
    val shapeButton = RoundedCornerShape(
        topStart = 16.dp,
        bottomEnd = 16.dp
    )

    LaunchedEffect(Unit) {
        viewModel.getImage(cardData.image)
    }

    Card(

        colors = CardDefaults.cardColors(
            containerColor = Block
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.width(160.dp)
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
                    modifier = Modifier.height(200.dp),
                ) {
                    val currentBitmap = viewModel.bitmap
                    if (currentBitmap != null) {
                        Image(
                            bitmap = currentBitmap.asImageBitmap(),
                            contentDescription = "Product",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth

                        )
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
                            viewModel.isFavorite = !viewModel.isFavorite
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
                    text = stringResource(R.string.best_seller).uppercase(),
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
            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(34.dp)
                    .clip(shapeButton)
                    .background(Accent),

            ) {
                Icon(
                    painter = painterResource(R.drawable.add),
                    contentDescription = null,
                    tint = Block,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}




@Preview
@Composable
private fun CardPreview() {
    CardProduct(
        CardData(
            name = "Газета для вас"
        )
    )
}