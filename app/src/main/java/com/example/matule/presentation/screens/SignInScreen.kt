package com.example.matule.presentation.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.matule.R
import com.example.matule.presentation.ui.theme.Accent
import com.example.matule.presentation.ui.theme.Background
import com.example.matule.presentation.ui.theme.Block
import com.example.matule.presentation.ui.theme.Disable
import com.example.matule.presentation.ui.theme.Hint
import com.example.matule.presentation.ui.theme.SubTextDark
import com.example.matule.presentation.ui.theme.TextColor
import com.example.matule.presentation.ui.theme.poppins
import com.example.matule.presentation.viewmodel.SignInViewModel

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = viewModel()
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Block)
            .padding(vertical = 20.dp, horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        item {
            Text(
                text = "Привет!",
                fontSize = 32.sp,
                lineHeight = 37.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                color = TextColor,

            )
            Text(
                text = "Заполните Свои данные или\nпродолжите через социальные медиа",
                fontSize = 16.sp,
                color = SubTextDark,
                fontFamily = poppins,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp,
            )
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 20.dp, horizontal = 10.dp),
            ) {
                Column {
                    Text(
                        "Email",
                        fontSize = 16.sp,
                        color = TextColor,
                        lineHeight = 20.sp,
                    )
                    Spacer(Modifier.height(10.dp))
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        maxLines = 1,
                        value = viewModel.login,
                        onValueChange = {
                            viewModel.login = it
                        },
                        placeholder = {
                            Text(
                                text = "xyz@gmail.com",
                                fontSize = 16.sp,
                                color = Hint,
                                fontFamily = poppins,
                                fontWeight = FontWeight.Medium
                            )
                        },
                        textStyle = TextStyle(
                            fontFamily = poppins,
                            fontSize = 16.sp
                        ),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Background,
                            focusedContainerColor = Background,
                            unfocusedIndicatorColor = Background,
                            focusedIndicatorColor = Background,
                            cursorColor = TextColor,
                            focusedTextColor = TextColor,
                            unfocusedTextColor = TextColor
                        ),
                        shape = RoundedCornerShape(15.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 20.dp, horizontal = 10.dp),
                ) {
                    Text(
                        "Password",
                        fontSize = 16.sp,
                        color = TextColor,
                        lineHeight = 20.sp,
                    )
                    Spacer(Modifier.height(10.dp))
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        maxLines = 1,
                        value = viewModel.password,
                        onValueChange = {
                            viewModel.password = it
                        },
                        placeholder = {
                            Text(
                                text = "••••••••",
                                fontSize = 16.sp,
                                color = Hint,
                                fontFamily = poppins,
                                fontWeight = FontWeight.Medium
                            )
                        },
                        textStyle = TextStyle(
                            fontFamily = poppins,
                            fontSize = 16.sp
                        ),
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    if (viewModel.visualPassword is PasswordVisualTransformation)
                                        viewModel.visualPassword = VisualTransformation.None
                                    else viewModel.visualPassword = PasswordVisualTransformation()
                                }
                            ) {
                                Icon(
                                    painter = if (viewModel.visualPassword is PasswordVisualTransformation) painterResource(
                                        R.drawable.eye_close
                                    )
                                    else painterResource(R.drawable.eye_open),
                                    contentDescription = "Visible password",
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Background,
                            focusedContainerColor = Background,
                            unfocusedIndicatorColor = Background,
                            focusedIndicatorColor = Background,
                            cursorColor = TextColor,
                            focusedTextColor = TextColor,
                            unfocusedTextColor = TextColor
                        ),
                        shape = RoundedCornerShape(15.dp),
                        visualTransformation = viewModel.visualPassword
                    )
                    Spacer(Modifier.height(10.dp))
                    TextButton(
                        onClick = {},
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            text = "Восстановить",
                            fontFamily = poppins,
                            fontSize = 12.sp,
                            color = SubTextDark,
                        )
                    }
                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Block,
                        containerColor = Accent,
                        disabledContainerColor = Disable
                    )
                ) {
                    Text(
                        text = "Войти",
                        fontFamily = poppins,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        item {
            TextButton(
                onClick = {}
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Вы впервые?",
                        color = Hint,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = "Создать пользователя",
                        color = TextColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    SignInScreen()
}