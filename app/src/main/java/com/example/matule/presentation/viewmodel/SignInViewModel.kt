package com.example.matule.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matule.data.Database.supabase
import com.example.matule.data.Repositories
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.exceptions.HttpRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.launch

class SignInViewModel: ViewModel() {
    var isLoading by mutableStateOf(false)
    var idUser by mutableStateOf<String?>(null)
    private val repositories = Repositories()
    var login by mutableStateOf("")
    var password by mutableStateOf("")
    var visibleDialog by mutableStateOf(false)
    var textError by mutableStateOf("")
    var visualPassword by mutableStateOf<VisualTransformation>(PasswordVisualTransformation())

    suspend fun signIn(login: String, password: String) {
            isLoading = true
            try {
                repositories.getUser(login, password)
                val currentUser = supabase.auth.currentUserOrNull()

                if (currentUser != null) {
                    idUser = currentUser.id
                }
            } catch (e: HttpRequestTimeoutException) {
                textError = "Время ожидания истекло"
                visibleDialog = true
            } catch (e: HttpRequestException) {
                textError = "Проблемы с сетью"
                visibleDialog = true
            } catch (e: AuthRestException) {
                textError = "Неправильный логин или пароль"
                visibleDialog = true
            } catch (e: Exception) {
                textError = "Неизвестная ошибка"
                visibleDialog = true
                Log.e("Error Auth", "$e")
            } finally{
                isLoading = false
            }
    }
}