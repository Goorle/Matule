package com.example.matule.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.matule.R
import com.example.matule.domain.font.poppins
import com.example.matule.presentation.components.PhoneVisualTransformation
import com.example.matule.presentation.ui.theme.Accent
import com.example.matule.presentation.ui.theme.Background
import com.example.matule.presentation.ui.theme.Block
import com.example.matule.presentation.ui.theme.Red
import com.example.matule.presentation.ui.theme.SubTextDark
import com.example.matule.presentation.ui.theme.TextColor
import com.example.matule.presentation.viewmodel.EditProfileViewModel
import com.example.matule.presentation.viewmodel.NotificationViewModel
import kotlinx.coroutines.launch

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = viewModel(),
    onClickBack: () -> Unit

) {
    val focusManager = LocalFocusManager.current
    val mask = "+7 (000) 000-00-00"

    val snackBarHost = remember { SnackbarHostState() }

    val textStyle = TextStyle(
        fontFamily = poppins,
        fontSize = 16.sp,
        color = TextColor,
        fontWeight = FontWeight.Medium
    )

    LaunchedEffect(viewModel.user) {
        val currentUser = viewModel.user
        if (currentUser != null) {
            viewModel.getImage(currentUser.userImage)
        }
    }

    Scaffold(
        topBar = {
            TopBarEditProfile(
                onClickBack = onClickBack
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHost) { data ->
                Snackbar(
                    containerColor = Background,
                    contentColor = TextColor,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                    content = {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Данные успешно изменены",
                                fontSize = 16.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight.Medium
                            )

                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Accent,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                )
            }
        },
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures {onTap ->
                focusManager.clearFocus()
            }
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
                    .padding(horizontal = 7.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                item{
                    val currentBitmap = viewModel.bitmap
                    Log.d("BITMAP", "$currentBitmap")
                    if (currentBitmap != null) {
                        Log.d("BITMAP", "$currentBitmap")
                        Image(
                            bitmap = currentBitmap.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(128.dp)
                                .clip(CircleShape)
                                .background(Background)
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.default_profile),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(128.dp)
                                .clip(CircleShape)
                                .background(Background)
                        )
                    }
                }

                item {
                    Column{
                        Text(
                            text = "Имя",
                            fontFamily = poppins,
                            fontSize = 16.sp,
                            color = SubTextDark,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(Modifier.height(10.dp))

                        OutlinedTextField(
                            value = viewModel.firstname,
                            onValueChange = {
                                viewModel.firstname = it
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Block,
                                unfocusedContainerColor = Background,
                                focusedBorderColor = Block,
                                focusedContainerColor = Background,
                                focusedTextColor = TextColor,
                                unfocusedTextColor = TextColor
                            ),
                            textStyle = textStyle,
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .clip(RoundedCornerShape(14.dp)),
                        )
                    }
                }

                item {
                    Column {
                        Text(
                            text = "Фамилия",
                            fontFamily = poppins,
                            fontSize = 16.sp,
                            color = SubTextDark,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(Modifier.height(10.dp))

                        OutlinedTextField(
                            value = viewModel.lastname,
                            onValueChange = {
                                viewModel.lastname = it
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Block,
                                unfocusedContainerColor = Background,
                                focusedBorderColor = Block,
                                focusedContainerColor = Background,
                                focusedTextColor = TextColor,
                                unfocusedTextColor = TextColor
                            ),
                            textStyle = textStyle,
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .clip(RoundedCornerShape(14.dp)),
                        )
                    }
                }

                item {
                    Column {
                        Text(
                            text = "Отчество",
                            fontFamily = poppins,
                            fontSize = 16.sp,
                            color = SubTextDark,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(Modifier.height(10.dp))

                        OutlinedTextField(
                            value = viewModel.secondname,
                            onValueChange = {
                                viewModel.secondname = it
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Block,
                                unfocusedContainerColor = Background,
                                focusedBorderColor = Block,
                                focusedContainerColor = Background,
                                focusedTextColor = TextColor,
                                unfocusedTextColor = TextColor
                            ),
                            textStyle = textStyle,
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .clip(RoundedCornerShape(14.dp)),
                        )
                    }
                }

                item {
                    Column {
                        Text(
                            text = "Номер телефона",
                            fontFamily = poppins,
                            fontSize = 16.sp,
                            color = SubTextDark,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(Modifier.height(10.dp))

                        OutlinedTextField(
                            value = viewModel.phone,
                            onValueChange = {
                                viewModel.onPhoneChanged(it.take(mask.count{it == '0'}))
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Block,
                                unfocusedContainerColor = Background,
                                focusedBorderColor = Block,
                                focusedContainerColor = Background,
                                focusedTextColor = TextColor,
                                unfocusedTextColor = TextColor
                            ),
                            textStyle = textStyle,
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .clip(RoundedCornerShape(14.dp)),
                            visualTransformation = PhoneVisualTransformation()
                        )
                    }
                }

                item {
                    Button(
                        onClick = {
                            viewModel.viewModelScope.launch {
                                viewModel.updateUser()
                                if (viewModel.isEditSuccess) {
                                    snackBarHost.showSnackbar("Данные изменены")
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Accent,
                            contentColor = Block,
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(50.dp)
                    ) {
                        if (viewModel.isLoading) {
                            CircularProgressIndicator(color = Block)
                        } else {
                            Text(
                                text = "Сохранить",
                                fontFamily = poppins,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
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
    viewModel: EditProfileViewModel
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
private fun TopBarEditProfile(
    onClickBack: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Профиль",
                fontFamily = poppins,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = TextColor,
                modifier = Modifier.fillMaxWidth()
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onClickBack,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Background
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
                onClick = {}
            ) { }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Block
        ),
        modifier = Modifier.padding(horizontal = 7.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun EditProfilePreview() {
    EditProfileScreen(
        onClickBack = {}
    )
}