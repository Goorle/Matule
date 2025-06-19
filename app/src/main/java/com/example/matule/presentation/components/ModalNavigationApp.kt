package com.example.matule.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.matule.R
import com.example.matule.domain.font.poppins
import com.example.matule.presentation.navigation.Routes
import com.example.matule.presentation.ui.theme.Accent
import com.example.matule.presentation.ui.theme.Background
import com.example.matule.presentation.ui.theme.Block
import com.example.matule.presentation.ui.theme.Red
import com.example.matule.presentation.ui.theme.TextColor
import com.example.matule.presentation.viewmodel.HomeViewModel
import com.example.matule.presentation.viewmodel.ModalNavigationViewModel
import okhttp3.Route

@Composable
fun ModalNavigationApp(
    viewModel: ModalNavigationViewModel = viewModel(),
    drawerState: DrawerState,
    navHostController: NavHostController,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Accent,
                drawerContentColor = Block,
                modifier = Modifier.fillMaxWidth(0.8f)

            ) {
                LaunchedEffect(viewModel.userData) {
                    val currentData = viewModel.userData
                    if (currentData != null) {
                        viewModel.getImage(currentData.userImage)
                    }
                }
                Box(
                    modifier = Modifier.padding(start = 25.dp, top = 15.dp)
                ) {
                    val currentBitmap = viewModel.bitmap
                    if (currentBitmap != null) {
                        Image(
                            bitmap = currentBitmap.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.default_profile),
                            contentDescription = null,
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .background(Background)
                        )
                    }
                }

                Text(
                    text = (viewModel.userData?.firstname + " " + viewModel.userData?.lastname),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppins,
                    color = Block,
                    modifier = Modifier.padding(start = 25.dp, top = 15.dp)
                )

                Spacer(Modifier.height(12.dp))

                NavigationDrawerItem(
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.profile),
                            contentDescription = null,
                            tint = Block
                        )
                    },
                    label = {
                        Text(
                            text = "Профиль",
                            fontSize = 20.sp
                        )
                    },
                    selected = true,
                    onClick = {
                        navHostController.navigate(Routes.Profile.route)
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = Block,
                        selectedTextColor = Block,
                        selectedContainerColor = Accent
                    ),
                    badge = {
                        Icon(
                            painter = painterResource(R.drawable.back_arrow),
                            contentDescription = null,
                            tint = Block,
                            modifier = Modifier.rotate(180f)
                        )
                    }
                )

                HorizontalDivider(color = Block, modifier = Modifier.padding(horizontal = 5.dp))

                Spacer(Modifier.height(12.dp))

                NavigationDrawerItem(
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.home),
                            contentDescription = null,
                            tint = Block
                        )
                    },
                    label = {
                        Text(
                            text = "Главная",
                            fontSize = 20.sp
                        )
                    },
                    selected = true,
                    onClick = {
                        navHostController.navigate(Routes.Home.route)
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = Block,
                        selectedTextColor = Block,
                        selectedContainerColor = Accent
                    ),
                    badge = {
                        Icon(
                            painter = painterResource(R.drawable.back_arrow),
                            contentDescription = null,
                            tint = Block,
                            modifier = Modifier.rotate(180f)
                        )
                    }
                )

                NavigationDrawerItem(
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.favorite_outlined),
                            contentDescription = null,
                            tint = Block
                        )
                    },
                    label = {
                        Text(
                            text = "Коллекция",
                            fontSize = 20.sp
                        )
                    },
                    selected = true,
                    onClick = {
                        navHostController.navigate(Routes.Favorite.route)
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = Block,
                        selectedTextColor = Block,
                        selectedContainerColor = Accent
                    ),
                    badge = {
                        Icon(
                            painter = painterResource(R.drawable.back_arrow),
                            contentDescription = null,
                            tint = Block,
                            modifier = Modifier.rotate(180f)
                        )
                    }
                )

                NavigationDrawerItem(
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.notification),
                            contentDescription = null,
                            tint = Block
                        )
                    },
                    label = {
                        Text(
                            text = "Уведомления",
                            fontSize = 20.sp
                        )
                    },
                    selected = true,
                    onClick = {
                        navHostController.navigate(Routes.Notification.route)
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = Block,
                        selectedTextColor = Block,
                        selectedContainerColor = Accent
                    ),
                    badge = {
                        Icon(
                            painter = painterResource(R.drawable.back_arrow),
                            contentDescription = null,
                            tint = Block,
                            modifier = Modifier.rotate(180f)
                        )
                    }
                )

                HorizontalDivider(color = Block, modifier = Modifier.padding(horizontal = 5.dp))

                Spacer(Modifier.height(12.dp))

                NavigationDrawerItem(
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.exit),
                            contentDescription = null,
                            tint = Block
                        )
                    },
                    label = {
                        Text(
                            text = "Выйти",
                            fontSize = 20.sp
                        )
                    },
                    selected = true,
                    onClick = {
                        viewModel.showDialogExit = true
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = Block,
                        selectedTextColor = Block,
                        selectedContainerColor = Accent
                    ),

                    )
            }
        },
        modifier = Modifier.background(Accent),
        content = content
    )

    if (viewModel.isLogout) {
        viewModel.signOut()

        navHostController.navigate(Routes.SignIn.route) {
            popUpTo(navHostController.graph.startDestinationId) {
                inclusive = true
            }

            launchSingleTop = true
        }
    }

    if (viewModel.showDialogExit) {
        DialogExit(viewModel)
    }
}

@Composable
fun DialogExit(
    viewModel: ModalNavigationViewModel
) {
    AlertDialog(
        onDismissRequest = {
            viewModel.showDialogExit = false
        },
        confirmButton = {
            Row (modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                Button(
                    onClick = {
                        viewModel.showDialogExit = false
                        viewModel.isLogout = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Accent,
                        contentColor = Block
                    ),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text(
                        text = "Да",
                        fontSize = 16.sp
                    )
                }
                Button(
                    onClick = {
                        viewModel.showDialogExit = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red,
                        contentColor = Block
                    ),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text(
                        text = "Нет",
                        fontSize = 16.sp
                    )
                }
            }
        },

        dismissButton = {

        },
        icon = {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = Accent,
                modifier = Modifier.size(48.dp)
            )
        },
        containerColor = Block,
        title = {
            Text(
                text = "Выход",
                fontSize = 24.sp,
                color = TextColor,
                fontFamily = poppins
            )
        },
        text = {
            Text(
                text = "Вы уверены, что хотите выйти?",
                fontSize = 14.sp,
                color = TextColor,
                fontFamily = poppins,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    )
}