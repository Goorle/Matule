package com.example.matule.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.matule.R
import com.example.matule.domain.font.poppins
import com.example.matule.presentation.ui.theme.Accent
import com.example.matule.presentation.ui.theme.Background
import com.example.matule.presentation.ui.theme.Block
import com.example.matule.presentation.ui.theme.Disable
import com.example.matule.presentation.ui.theme.Hint
import com.example.matule.presentation.ui.theme.SubTextDark
import com.example.matule.presentation.ui.theme.TextColor
import com.example.matule.presentation.viewmodel.SignInViewModel

@Composable
fun SignIn(
    viewModel: SignInViewModel = viewModel()
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


        Column(

        ) {
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
                onClick = {},
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
                Text(
                    text = stringResource(R.string.sign_in),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        TextButton(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = Block,
                contentColor = SubTextDark
            )
        ) {
            Text(
                text = stringResource(R.string.new_create_user),
                color = Hint,
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    SignIn()
}