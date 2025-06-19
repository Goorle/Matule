package com.example.matule.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
import com.example.matule.presentation.ui.theme.Hint
import com.example.matule.presentation.ui.theme.Red
import com.example.matule.presentation.ui.theme.SubTextDark
import com.example.matule.presentation.ui.theme.TextColor
import com.example.matule.presentation.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    onClickBack: () -> Unit,
    onSuccessfullyReg: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    val colorContainer =  OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Background,
        focusedContainerColor = Background,
        unfocusedBorderColor = Background,
        unfocusedContainerColor = Background,
        focusedTextColor = TextColor,
        unfocusedTextColor = TextColor,
        errorTextColor = Red,
        errorBorderColor = Background,
        errorContainerColor = Background
    )
    Scaffold(
        containerColor = Block
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Block)
                    .padding(horizontal = 10.dp)
                    .verticalScroll(state = rememberScrollState())
                    .pointerInput(Unit) {
                        detectTapGestures {onTap ->
                            focusManager.clearFocus()

                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = onClickBack,
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Background
                            ),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                                contentDescription = null,
                                tint = TextColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Text(
                        text = "Регистрация",
                        fontFamily = poppins,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextColor,
                        lineHeight = 32.sp
                    )

                    Text(
                        text = "Заполните свои данные",
                        fontFamily = poppins,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        color = SubTextDark,
                        fontWeight = FontWeight.Normal
                    )
                }
                Column {
                    Text(
                        text = "Ваше имя",
                        fontFamily = poppins,
                        fontSize = 16.sp,
                        color = TextColor,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(Modifier.height(5.dp))

                    OutlinedTextField(
                        value = viewModel.name,
                        onValueChange = {
                            viewModel.name = it
                            viewModel.isNameValidate = viewModel.validateEnterName(it)
                        },
                        placeholder = {
                            Text(
                                text = "xxxxxxxx",
                                fontFamily = poppins,
                                color = Hint,
                                fontSize = 14.sp
                            )
                        },
                        singleLine = true,
                        colors = colorContainer,
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Medium
                        ),
                        isError = viewModel.isNameValidate,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth(0.9f)
                    )
                }

                Column {
                    Text(
                        text = "Email",
                        fontFamily = poppins,
                        fontSize = 16.sp,
                        color = TextColor,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(Modifier.height(5.dp))

                    OutlinedTextField(
                        value = viewModel.login,
                        onValueChange = {
                            viewModel.login = it
                            viewModel.isEmailValidate = viewModel.validateEmail(it)
                        },
                        placeholder = {
                            Text(
                                text = "xyz@gmail.com",
                                fontFamily = poppins,
                                color = Hint,
                                fontSize = 14.sp
                            )
                        },
                        singleLine = true,
                        isError = viewModel.isEmailValidate,
                        colors = colorContainer,
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Medium
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth(0.9f)
                    )
                }

                Column {
                    Text(
                        text = "Пароль",
                        fontFamily = poppins,
                        fontSize = 16.sp,
                        color = TextColor,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(Modifier.height(5.dp))

                    OutlinedTextField(
                        value = viewModel.password,
                        onValueChange = {
                            viewModel.password = it
                            viewModel.isPasswordValidate = false
                            viewModel.isReEnterPasswordValidate = false
                        },
                        placeholder = {
                            Text(
                                text = "********",
                                fontFamily = poppins,
                                color = Hint,
                                fontSize = 14.sp
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    viewModel.visualPassword =
                                        viewModel.changePasswordVisual(viewModel.visualPassword)
                                }
                            ) {
                                val painterIcon = when (viewModel.visualPassword) {
                                    is PasswordVisualTransformation -> painterResource(R.drawable.eye_closed)
                                    else -> painterResource(R.drawable.eye_open)
                                }

                                Icon(
                                    painter = painterIcon,
                                    contentDescription = null,
                                    tint = TextColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        },
                        singleLine = true,
                        isError = viewModel.isPasswordValidate,
                        colors = colorContainer,
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Medium
                        ),
                        visualTransformation = viewModel.visualPassword,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth(0.9f)
                    )
                }

                Column {
                    Text(
                        text = "Повторите пароль",
                        fontFamily = poppins,
                        fontSize = 16.sp,
                        color = TextColor,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(Modifier.height(5.dp))

                    OutlinedTextField(
                        value = viewModel.reEnterPassword,
                        onValueChange = {
                            viewModel.reEnterPassword = it
                            viewModel.isPasswordValidate = false
                            viewModel.isReEnterPasswordValidate = false
                        },
                        placeholder = {
                            Text(
                                text = "********",
                                fontFamily = poppins,
                                color = Hint,
                                fontSize = 14.sp
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    viewModel.visualReEnterPassword =
                                        viewModel.changePasswordVisual(viewModel.visualReEnterPassword)
                                }
                            ) {
                                val painterIcon = when (viewModel.visualReEnterPassword) {
                                    is PasswordVisualTransformation -> painterResource(R.drawable.eye_closed)
                                    else -> painterResource(R.drawable.eye_open)
                                }

                                Icon(
                                    painter = painterIcon,
                                    contentDescription = null,
                                    tint = TextColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        },
                        singleLine = true,
                        isError = viewModel.isReEnterPasswordValidate,
                        colors = colorContainer,
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Medium
                        ),
                        visualTransformation = viewModel.visualReEnterPassword,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth(0.9f),
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Checkbox(
                        checked = viewModel.isConfirmPolicy,
                        onCheckedChange = {
                            viewModel.isConfirmPolicy = it
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Accent,
                            uncheckedColor = Accent
                        ),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = "Даю согласие на обработку персональных данных",
                        fontFamily = poppins,
                        fontSize = 16.sp,
                        color = Hint,
                        fontWeight = FontWeight.Medium
                    )
                }

                Button(
                    onClick = {
                        viewModel.viewModelScope.launch {
                            if (viewModel.validateUserData()) {
                                viewModel.registrationUser(
                                    viewModel.login,
                                    viewModel.password,
                                    viewModel.name
                                )
                                if (viewModel.regSuccess) {
                                    onSuccessfullyReg()
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Accent
                    ),

                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(0.9f),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    if (viewModel.isLoading) {
                        CircularProgressIndicator(color = Block)
                    } else {
                        Text(
                            text = "Зарегистрироваться",
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold,
                            color = Background,
                            fontSize = 16.sp
                        )
                    }
                }

                TextButton(
                    onClick = onClickBack,

                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Есть аккаунт?",
                        fontFamily = poppins,
                        fontSize = 16.sp,
                        color = Hint
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = "Войти",
                        fontFamily = poppins,
                        fontSize = 16.sp,
                        color = TextColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }

    if (viewModel.visibleError) {
        DialogError(viewModel)
    }
}

@Composable
private fun DialogError(
    viewModel: RegisterViewModel
) {
    AlertDialog(
        onDismissRequest = {
            viewModel.visibleError = false
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.visibleError = false
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
private fun RegisterPreview() {
    RegisterScreen(
        onClickBack = {},
        onSuccessfullyReg = {}
    )
}