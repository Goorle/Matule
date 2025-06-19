package com.example.matule.presentation.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import com.example.matule.presentation.components.ModalNavigationApp
import com.example.matule.presentation.components.PhoneVisualTransformation
import com.example.matule.presentation.ui.theme.Accent
import com.example.matule.presentation.ui.theme.Background
import com.example.matule.presentation.ui.theme.Block
import com.example.matule.presentation.ui.theme.Red
import com.example.matule.presentation.ui.theme.TextColor
import com.example.matule.presentation.viewmodel.HomeViewModel
import com.example.matule.presentation.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun UserScreen(
    onClickEditProfile: () -> Unit,
    viewModel: UserViewModel = viewModel(),
    navHostController: NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    LaunchedEffect(viewModel.userData) {
        val currentData = viewModel.userData
        if (currentData != null) {
            viewModel.getImage(currentData.userImage)
        }
    }
    ModalNavigationApp(drawerState = drawerState, navHostController = navHostController) {
        Scaffold(
            topBar = {
                TopBarUser(
                    drawerState,
                    onClickEditProfile = onClickEditProfile
                )
            },
            bottomBar = {
                BottomAppBar(navHostController)
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Block)
                    .padding(innerPadding)
            ) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 15.dp, horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val currentBitmap = viewModel.bitmap
                            if (currentBitmap != null) {
                                Image(
                                    bitmap = currentBitmap.asImageBitmap(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(128.dp)
                                        .clip(CircleShape)
                                )
                            } else {
                                Image(
                                    painter = painterResource(R.drawable.default_profile),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(128.dp)
                                        .clip(CircleShape)
                                        .background(Background)
                                )
                            }


                            Spacer(Modifier.height(15.dp))

                            Text(
                                text = "${viewModel.firstname} ${viewModel.lastname}",
                                fontFamily = poppins,
                                fontSize = 20.sp,
                                color = TextColor,
                                fontWeight = FontWeight.SemiBold
                            )
                            TextButton(
                                onClick = {
                                },
                                contentPadding = PaddingValues(5.dp),
                            ) {
                                Text(
                                    text = "Изменить фото профиля",
                                    fontFamily = poppins,
                                    fontSize = 12.sp,
                                    color = Accent,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    item {
                        Spacer(Modifier.height(10.dp))
                        Column {
                            Text(
                                text = "Имя",
                                fontFamily = poppins,
                                color = TextColor,
                                fontSize = 16.sp
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Background, RoundedCornerShape(14.dp))
                                    .padding(vertical = 12.dp, horizontal = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = viewModel.firstname,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = TextColor
                                )

                                val color = if (viewModel.firstname.isEmpty()) Red else Accent
                                val image =
                                    if (viewModel.firstname.isEmpty()) Icons.Default.Clear else Icons.Default.Check
                                Icon(
                                    imageVector = image,
                                    contentDescription = null,
                                    tint = color,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    item {
                        Spacer(Modifier.height(10.dp))
                        Column {
                            Text(
                                text = "Фамилия",
                                fontFamily = poppins,
                                color = TextColor,
                                fontSize = 16.sp
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Background, RoundedCornerShape(14.dp))
                                    .padding(vertical = 12.dp, horizontal = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = viewModel.lastname,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = TextColor
                                )
                                val color = if (viewModel.lastname.isEmpty()) Red else Accent
                                val image =
                                    if (viewModel.lastname.isEmpty()) Icons.Default.Clear else Icons.Default.Check
                                Icon(
                                    imageVector = image,
                                    contentDescription = null,
                                    tint = color,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    item {
                        Spacer(Modifier.height(10.dp))
                        Column {
                            Text(
                                text = "Отчество",
                                fontFamily = poppins,
                                color = TextColor,
                                fontSize = 16.sp
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Background, RoundedCornerShape(14.dp))
                                    .padding(vertical = 12.dp, horizontal = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = viewModel.secondname,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = TextColor
                                )

                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Accent,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    item {
                        Spacer(Modifier.height(10.dp))
                        Column {
                            Text(
                                text = "Почта",
                                fontFamily = poppins,
                                color = TextColor,
                                fontSize = 16.sp
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Background, RoundedCornerShape(14.dp))
                                    .padding(vertical = 12.dp, horizontal = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = viewModel.email,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = TextColor
                                )
                                val color = if (viewModel.email.isEmpty()) Red else Accent
                                val image =
                                    if (viewModel.email.isEmpty()) Icons.Default.Clear else Icons.Default.Check
                                Icon(
                                    imageVector = image,
                                    contentDescription = null,
                                    tint = color,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    item {
                        Spacer(Modifier.height(10.dp))
                        Column {
                            Text(
                                text = "Номер телефона",
                                fontFamily = poppins,
                                color = TextColor,
                                fontSize = 16.sp
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Background, RoundedCornerShape(14.dp))
                                    .padding(end = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                OutlinedTextField(
                                    value = viewModel.phone,
                                    onValueChange = {},
                                    visualTransformation = PhoneVisualTransformation(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        unfocusedBorderColor = Background,
                                        unfocusedContainerColor = Background,
                                        focusedBorderColor = Block,
                                        focusedContainerColor = Background,
                                        focusedTextColor = TextColor,
                                        unfocusedTextColor = TextColor,
                                        disabledTextColor = TextColor,
                                        disabledBorderColor = Background
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    textStyle = TextStyle(
                                        fontSize = 14.sp,
                                        fontFamily = poppins,
                                    ),
                                    enabled = false,
                                )

                                val color = if (viewModel.phone.isEmpty()) Red else Accent
                                val image =
                                    if (viewModel.phone.isEmpty()) Icons.Default.Clear else Icons.Default.Check
                                Icon(
                                    imageVector = image,
                                    contentDescription = null,
                                    tint = color,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    item {
                        Spacer(Modifier.height(10.dp))
                        Column {
                            Text(
                                text = "Статус подписки",
                                fontFamily = poppins,
                                fontSize = 16.sp,
                                color = TextColor
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Background, RoundedCornerShape(14.dp))
                                    .padding(vertical = 12.dp, horizontal = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (viewModel.subscription) "Активен" else "Не активен",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = TextColor
                                )

                                val color = if (!viewModel.subscription) Red else Accent
                                val image =
                                    if (!viewModel.subscription) Icons.Default.Clear else Icons.Default.Check
                                Icon(
                                    imageVector = image,
                                    contentDescription = null,
                                    tint = color,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
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
    viewModel: UserViewModel
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
private fun TopBarUser(
    drawerState: DrawerState,
    onClickEditProfile: () -> Unit
) {
    val scope = rememberCoroutineScope()
    TopAppBar(
        title = {
            Text(
                text = "Профиль",
                fontFamily = poppins,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
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
                onClick = onClickEditProfile,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Background
                ),
                modifier = Modifier.size(44.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
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

@Composable
@Preview(showBackground = true)
fun UserPreview() {
    UserScreen(
        onClickEditProfile = {},
        navHostController = rememberNavController()
    )
}
