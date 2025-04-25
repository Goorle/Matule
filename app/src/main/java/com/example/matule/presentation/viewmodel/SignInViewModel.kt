package com.example.matule.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matule.data.Repositories
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.launch

class SignInViewModel: ViewModel() {
    var isLoading by mutableStateOf(false)
    var idUser by mutableStateOf<UserInfo?>(null)
    private val repositories = Repositories()
    var login by mutableStateOf("")
    var password by mutableStateOf("")
    var visibleDialog by mutableStateOf(false)
    var textError by mutableStateOf("")
    var visualPassword by mutableStateOf<VisualTransformation>(PasswordVisualTransformation())

    suspend fun signIn(email: String, password: String) {
        repositories.getBooksWithDetails()
        isLoading = true
        try {
            repositories.signInUser(email, password)
            idUser = repositories.getCurrentUser()
        } catch (e: RestException) {
            textError = "Неверный логин или пароль"
            visibleDialog = true
        } catch (e: HttpRequestTimeoutException) {
            textError = "Время ожидания закончилось"
            visibleDialog = true
        } catch (e: HttpRequestException) {
            textError = "Неполадки с сетью"
            visibleDialog = true
        } finally {
            isLoading = false
        }
    }
}