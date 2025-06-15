package com.example.matule.presentation.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matule.data.Repositories
import com.example.matule.domain.models.User
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.postgrest.exception.PostgrestRestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.launch

class EditProfileViewModel: ViewModel() {
    private val repositories = Repositories()
    var user: User? by mutableStateOf<User?>(null)
    var isLoading by mutableStateOf(false)
    var isEditSuccess by mutableStateOf(false)

    var bitmap by mutableStateOf<Bitmap?>(null)

    var firstname by mutableStateOf("")
    var lastname by mutableStateOf("")
    var secondname by mutableStateOf("")
    var phone by mutableStateOf("")

    var messageText by mutableStateOf("")
    var isVisibleMessage by mutableStateOf(false)

    init {
        loadUser()
    }

    suspend fun getImage(imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            try {
                val list = imageUrl.split("/")
                val bucket = list[0]
                val file = list[1]
                val byteArray = repositories.getFileFromStorage(bucket, file)
                bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            } catch (_: PostgrestRestException) {
                messageText = "Сервер временно недоступен. Попробуйте повторить попытку позже"
                isVisibleMessage = true
            } catch (_: HttpRequestTimeoutException) {
                messageText = "Не удалось соединиться с сервером. Проверьте ваше интернет-соединение"
                isVisibleMessage = true
            } catch(_: HttpRequestException) {
                messageText = "Проблемы с соединением. Проверьте ваше подключение к интернету"
                isVisibleMessage = true
            }
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            try {
                user = repositories.getUserData()
                updateFields()
            } catch (_: PostgrestRestException) {
                messageText = "Сервер временно недоступен. Попробуйте повторить попытку позже"
                isVisibleMessage = true
            } catch (_: HttpRequestTimeoutException) {
                messageText = "Не удалось соединиться с сервером. Проверьте ваше интернет-соединение"
                isVisibleMessage = true
            } catch(_: HttpRequestException) {
                messageText = "Проблемы с соединением. Проверьте ваше подключение к интернету"
                isVisibleMessage = true
            }
        }
    }

    private fun updateFields() {
        viewModelScope.launch {
            try {
                val currentUser = user
                if (currentUser != null) {
                    firstname = currentUser.firstname
                    lastname = currentUser.lastname ?: ""
                    secondname = currentUser.secondName
                    phone = currentUser.phone
                }
            } catch (e: Exception) {

            }
        }
    }

    suspend fun updateUser() {
        try {
            isLoading = true
            val currentUser = user

            if (currentUser != null) {

                currentUser.firstname = firstname
                currentUser.lastname = lastname
                currentUser.secondName = secondname
                currentUser.phone = phone
                repositories.updateUserData(currentUser)

                isEditSuccess = true
            }
        } catch (_: PostgrestRestException) {
            messageText = "Сервер временно недоступен. Попробуйте повторить попытку позже"
            isVisibleMessage = true
        } catch (_: HttpRequestTimeoutException) {
            messageText = "Не удалось соединиться с сервером. Проверьте ваше интернет-соединение"
            isVisibleMessage = true
        } catch(_: HttpRequestException) {
            messageText = "Проблемы с соединением. Проверьте ваше подключение к интернету"
            isVisibleMessage = true
        }
    }

    fun onPhoneChanged(it: String) {
        phone = it
    }
}