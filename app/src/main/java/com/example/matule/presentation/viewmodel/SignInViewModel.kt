package com.example.matule.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel

class SignInViewModel: ViewModel() {
    var login by mutableStateOf("")
    var password by mutableStateOf("")
    var visualPassword by mutableStateOf<VisualTransformation>(PasswordVisualTransformation())
}