package com.example.matule.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.matule.R
import com.example.matule.domain.font.poppins
import com.example.matule.presentation.ui.theme.Accent
import com.example.matule.presentation.ui.theme.Background
import com.example.matule.presentation.ui.theme.Block
import com.example.matule.presentation.ui.theme.Disable
import com.example.matule.presentation.ui.theme.Hint
import com.example.matule.presentation.ui.theme.Red
import com.example.matule.presentation.ui.theme.SubTextDark
import com.example.matule.presentation.ui.theme.TextColor
import com.example.matule.presentation.viewmodel.SignInViewModel
import kotlinx.coroutines.launch

@Composable
fun SignIn(
    viewModel: SignInViewModel = viewModel(),
    onClickSignIn: () -> Unit,
    onClickRegister: () -> Unit
) {
    val textFieldColors = TextFieldDefaults.colors(
        unfocusedTextColor = TextColor,
        focusedTextColor = TextColor,
        unfocusedContainerColor = Background,
        focusedContainerColor = Background,
        focusedIndicatorColor = Block,
        unfocusedIndicatorColor = Block,
        cursorColor = TextColor
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Block)
            .padding(vertical = 20.dp, horizontal = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.hello),
                fontSize = 32.sp,
                color = TextColor,
                fontFamily = poppins,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(15.dp))
            Text(
                text = stringResource(R.string.fill_details),
                fontSize = 16.sp,
                color = SubTextDark,
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }



        Column{
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.email),
                    fontSize = 16.sp,
                    fontFamily = poppins,
                    color = TextColor,
                    fontWeight = FontWeight.Medium
                )

                TextField(
                    value = viewModel.login,
                    onValueChange = {
                        viewModel.login = it
                    },
                    placeholder = {
                        Text(
                            text = "xyz@gmail.com",
                            fontFamily = poppins,
                            color = Hint,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = textFieldColors,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Spacer(Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.password),
                    fontFamily = poppins,
                    fontSize = 16.sp,
                    color = TextColor,
                    fontWeight = FontWeight.Medium
                )

                TextField(
                    value = viewModel.password,
                    onValueChange = {
                        viewModel.password = it
                    },
                    placeholder = {
                        Text(
                            text = "********",
                            fontFamily = poppins,
                            color = Hint,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                viewModel.visualPassword = if (viewModel.visualPassword is PasswordVisualTransformation) VisualTransformation.None else PasswordVisualTransformation()
                            }
                        ) {
                            val painter = if (viewModel.visualPassword is PasswordVisualTransformation) painterResource(R.drawable.eye_closed) else painterResource(R.drawable.eye_open)
                            Icon(
                                painter = painter,
                                contentDescription = "Visual password",
                                tint = Hint,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    visualTransformation = viewModel.visualPassword,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = textFieldColors
                )

                TextButton(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Block,
                        contentColor = SubTextDark
                    ),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(
                        text = stringResource(R.string.recovery_pass),
                        fontFamily = poppins,
                        color = SubTextDark,
                        fontSize = 12.sp,
                    )
                }
            }

            Button(
                onClick = {
                    if (viewModel.login.isEmpty() && viewModel.password.isEmpty()) {
                        viewModel.visibleDialog = true
                        viewModel.textError = "Пожалуйста, заполните все поля"
                    } else {
                        viewModel.viewModelScope.launch {
                            viewModel.signIn(viewModel.login, viewModel.password)
                            if (viewModel.idUser != null) {
                                onClickSignIn()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Accent,
                    contentColor = Background,
                    disabledContainerColor = Disable
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(color = Block)
                } else {


                    Text(
                        text = stringResource(R.string.sign_in),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        TextButton(
            onClick = onClickRegister,
            colors = ButtonDefaults.buttonColors(
                containerColor = Block,
                contentColor = SubTextDark
            )
        ) {
            Text(
                text = "Вы впервые?",
                color = Hint,
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )

            Spacer(Modifier.width(5.dp))

            Text(
                text = "Создать пользователя",
                color = TextColor,
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        }
    }

    if (viewModel.visibleDialog) {
        DialogError(viewModel)
    }
}

@Composable
fun DialogError(
    viewModel: SignInViewModel
    ) {
    AlertDialog(
        onDismissRequest = {
            viewModel.visibleDialog = false
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.visibleDialog = false
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
                text = viewModel.textError,
                fontSize = 14.sp,
                color = TextColor,
                fontFamily = poppins
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
private fun SignInPreview() {
    SignIn(
        onClickSignIn = {},
        onClickRegister = {}
    )
}