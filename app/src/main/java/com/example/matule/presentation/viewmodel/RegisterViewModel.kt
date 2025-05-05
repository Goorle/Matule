package com.example.matule.presentation.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import com.example.matule.data.Repositories
import io.github.jan.supabase.auth.exception.AuthErrorCode
import io.github.jan.supabase.auth.exception.AuthRestException

class RegisterViewModel: ViewModel() {
    private val repository = Repositories()
    var login by mutableStateOf("")
    var name by mutableStateOf("")
    var password by mutableStateOf("")
    var reEnterPassword by mutableStateOf("")
    var isConfirmPolicy by mutableStateOf(false)
    var visualPassword by mutableStateOf<VisualTransformation>(PasswordVisualTransformation())
    var visualReEnterPassword by mutableStateOf<VisualTransformation>(PasswordVisualTransformation())

    var visibleError by mutableStateOf(false)
    var textError by mutableStateOf("")

    var isNameValidate by mutableStateOf(false)
    var isEmailValidate by mutableStateOf(false)
    var isPasswordValidate by mutableStateOf(false)
    var isReEnterPasswordValidate by mutableStateOf(false)

    var isLoading by mutableStateOf(false)
    var regSuccess by mutableStateOf(false)

    suspend fun registrationUser(email: String, password: String, name: String) {
        isLoading = true
        try {
            repository.signUpUser(email, password, name)
            regSuccess = true
        } catch (e: AuthRestException) {
            when (e.errorCode) {
                AuthErrorCode.UserAlreadyExists -> textError = "Пользователь с таким email адресом уже есть"
                AuthErrorCode.WeakPassword -> {
                    textError = "Пароль должен состоять более 6 символов"
                    isPasswordValidate = true
                }
                else -> "Ошибка регистрации"
            }
            visibleError = true
        } finally {
            isLoading = false
        }
    }

    fun changePasswordVisual(visualPassword: VisualTransformation): VisualTransformation {
        if (visualPassword is PasswordVisualTransformation) {
            return VisualTransformation.None
        }
        return PasswordVisualTransformation()
    }

    fun validateUserData(): Boolean {
        if (isNameValidate) {
            textError = "Введено неккоректное имя"
            visibleError = true
            return false
        }

        if (isEmailValidate) {
            textError = "Введено неккоректный email адрес"
            visibleError = true
            return false
        }

        if (isEmptyFields()) {
            textError = "Пожалуйста, заполните все поля ввода"
            visibleError = true
            return false
        }
        if (!isConfirmPolicy){
            textError = "Для продолжения нужно согласие на обработку персональных данных"
            visibleError = true
            return false
        }
        if (password != reEnterPassword) {
            textError = "Введенные вами пароли должны совпадать"
            visibleError = true
            isPasswordValidate = true
            isReEnterPasswordValidate = true
            return false
        }
        return true
    }

    private fun isEmptyFields(): Boolean {
        return login.isEmpty() ||
                name.isEmpty() ||
                password.isEmpty() ||
                reEnterPassword.isEmpty()

    }

    fun validateEnterName(name: String): Boolean {
        return !name.matches(Regex("^[a-zA-Zа-яА-ЯёЁ]+$")) || name.length < 2
    }

    fun validateEmail(email: String): Boolean {
        return !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}