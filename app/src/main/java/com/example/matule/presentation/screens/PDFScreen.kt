package com.example.matule.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.matule.presentation.ui.theme.Accent
import com.example.matule.presentation.ui.theme.Block
import com.rajat.pdfviewer.PdfRendererView
import com.rajat.pdfviewer.compose.PdfRendererViewCompose
import com.rajat.pdfviewer.util.PdfSource

@Composable
fun MyPdfScreenFromUrl(
    url: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var loadingIndicator by remember { mutableStateOf(true) }

    Box(modifier = modifier.fillMaxSize()) {
        PdfRendererViewCompose(
            source = PdfSource.Remote(url),
            modifier = Modifier
                .fillMaxSize(),
            lifecycleOwner = lifecycleOwner,
            statusCallBack = object : PdfRendererView.StatusCallBack {
                override fun onPdfLoadStart() {
                    Log.i("statusCallBack", "onPdfLoadStart")
                }

                override fun onPdfLoadProgress(progress: Int, downloadedBytes: Long, totalBytes: Long?) {
                    Log.i("progressCallBack", "Progress: $progress")
                }

                override fun onPdfLoadSuccess(absolutePath: String) {
                    loadingIndicator = false
                    Log.i("statusCallBack", "onPdfLoadSuccess: $absolutePath")
                }

                override fun onError(error: Throwable) {
                    Log.e("statusCallBack", "onError: ${error.message}")
                }

                override fun onPageChanged(currentPage: Int, totalPage: Int) {}
            },
            zoomListener = object : PdfRendererView.ZoomListener {
                override fun onZoomChanged(isZoomedIn: Boolean, scale: Float) {

                }
            },
            onReady = {
            }
        )


        if (loadingIndicator) {
            IndicatorDownload()
        }
        Box(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            IconButton(
                onClick = onClose,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Black.copy(alpha = 0.7f)
                ),
                modifier = Modifier.size(54.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null,
                    tint = Block,
                    modifier = Modifier.size(32.dp)
                )
            }

        }


    }
}


@Composable
fun IndicatorDownload() {
    Box(
        modifier = Modifier.fillMaxSize().
        background(Color.Black.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Загрузка",
                color = White,
                fontSize = 24.sp
            )
            Spacer(Modifier.padding(15.dp))
            CircularProgressIndicator(color = Accent)
        }
    }
}