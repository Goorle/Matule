package com.example.matule.presentation.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matule.domain.font.poppins
import com.example.matule.presentation.ui.theme.Accent
import com.example.matule.presentation.ui.theme.Block
import com.example.matule.presentation.ui.theme.TextColor
import com.pratikk.jetpdfvue.HorizontalVueReader
import com.pratikk.jetpdfvue.state.HorizontalVueReaderState
import com.pratikk.jetpdfvue.state.VueFileType
import com.pratikk.jetpdfvue.state.VueLoadState
import com.pratikk.jetpdfvue.state.VueResourceType
import com.pratikk.jetpdfvue.state.rememberHorizontalVueReaderState
import kotlinx.coroutines.launch

@Composable
fun PDFViewer(
    url: String,
    onClickBack: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val horizontalVueReaderState = rememberHorizontalVueReaderState(
        resource = VueResourceType.Remote(
            url,
            fileType = VueFileType.PDF
        )
    )

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val boxConstraints = this
        val configuration = LocalConfiguration.current
        val containerSize = remember {
            IntSize(boxConstraints.constraints.maxWidth, boxConstraints.constraints.maxHeight)
        }

        LaunchedEffect(Unit) {
            try {
                horizontalVueReaderState.load(
                    context = context,
                    coroutineScope = coroutineScope,
                    containerSize = containerSize,
                    isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT,
                    customResource = null
                )
            } catch (e: IllegalStateException) {
                Toast.makeText(context, "$e", Toast.LENGTH_LONG).show()
            }

        }

        val vueLoadState = horizontalVueReaderState.vueLoadState
        when (vueLoadState) {
            is VueLoadState.NoDocument -> {
                Button(onClick = {

                }) {
                    Text(text = "Import Document")
                }
            }

            is VueLoadState.DocumentError -> {
                Column {
                    Text(text = "Error:  ${horizontalVueReaderState.vueLoadState.getErrorMessage}")
                    Button(onClick = {
                        coroutineScope.launch {
                            horizontalVueReaderState.load(
                                context = context,
                                coroutineScope = coroutineScope,
                                containerSize = containerSize,
                                isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT,
                                customResource = null
                            )
                        }
                    }) {
                        Text(text = "Retry")
                    }
                }
            }

            is VueLoadState.DocumentImporting -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Text(text = "Importing...")
                }
            }

            is VueLoadState.DocumentLoaded -> {
                HorizontalViewPdf(horizontalVueReaderState)
            }

            is VueLoadState.DocumentLoading -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = Accent, modifier = Modifier.padding(bottom = 20.dp))
                    Text(text = "Загрузка ${horizontalVueReaderState.loadPercent}",
                        fontSize = 24.sp,
                        fontFamily = poppins,
                        color = TextColor
                        )
                }
            }
        }
    }
}

@Composable
private fun HorizontalViewPdf(
    horizontalVueReaderState: HorizontalVueReaderState
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalVueReader(
            modifier = Modifier.fillMaxSize(),
            contentModifier = Modifier.fillMaxSize(),
            horizontalVueReaderState = horizontalVueReaderState
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 20.dp),

            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NavigationButtons(
                onClickNext = {},
                onClickBack = {}
            )
        }
    }

}


@Composable
private fun NavigationButtons(
    onClickNext: () -> Unit,
    onClickBack: () -> Unit
) {
    IconButton(
        onClick = onClickBack,
        modifier = Modifier
            .background(color = Block.copy(alpha = 0.75f), shape = RoundedCornerShape(15.dp))
            .border(1.dp, TextColor, shape = RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(15.dp))
            .size(52.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = null,
            tint = TextColor,
            modifier = Modifier.size(32.dp)
        )
    }

    IconButton(
        onClick = onClickNext,
        modifier = Modifier
            .background(color = Block.copy(alpha = 0.75f), shape = RoundedCornerShape(15.dp))
            .border(1.dp, TextColor, shape = RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(15.dp))
            .size(52.dp)

    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = TextColor,
            modifier = Modifier.size(32.dp)
        )
    }
}
