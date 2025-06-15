package com.example.matule.presentation.screens

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.matule.presentation.components.BottomAppBar
import com.example.matule.presentation.components.CardNotification
import com.example.matule.presentation.ui.theme.Accent
import com.example.matule.presentation.ui.theme.Block
import com.example.matule.presentation.ui.theme.Red
import com.example.matule.presentation.ui.theme.TextColor
import com.example.matule.presentation.viewmodel.HomeViewModel
import com.example.matule.presentation.viewmodel.NotificationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = viewModel(),
    navHostController: NavHostController,
) {
    LaunchedEffect(Unit) {
        viewModel.startRealtime(this)
    }

    Scaffold(
        topBar = {
            NotificationTopBar(
            )
        },
        bottomBar = {
            BottomAppBar(navHostController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier
                .fillMaxSize()
                .background(Block)
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter

        ) {
            if (viewModel.notification.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    items(viewModel.notification) {
                        CardNotification(it)
                        Spacer(Modifier.height(10.dp))
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight().align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            tint = Accent,
                            modifier = Modifier.size(48.dp))
                        Spacer(Modifier.height(16.dp))

                        Text(
                            text = "Нет новых уведомлений",
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp,
                            color = TextColor,
                            textAlign = TextAlign.Center,
                            lineHeight = 30.sp
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = "Как только что-то появится, мы вас оповестим",
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

    if (viewModel.isVisibleMessage) {
        DialogError(viewModel)
    }
}

@Composable
fun DialogError(
    viewModel: NotificationViewModel
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
private fun NotificationTopBar(
) {
    TopAppBar(
        title = {
            Text(
                text = "Уведомления",
                fontFamily = poppins,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Block,
            navigationIconContentColor = TextColor,
            titleContentColor = TextColor,
            actionIconContentColor = TextColor
        ),
        modifier = Modifier.padding(horizontal = 7.dp)
    )
}

@Preview
@Composable
private fun NotificationPreview() {
    NotificationScreen(
        navHostController = rememberNavController()
    )
}