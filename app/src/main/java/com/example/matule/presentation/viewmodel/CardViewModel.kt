package com.example.matule.presentation.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matule.data.Repositories
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.postgrest.exception.PostgrestRestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.launch

class CardViewModel: ViewModel() {
    private val repository = Repositories()
    var isFavorite by mutableStateOf(false)
    var bitmap by mutableStateOf<Bitmap?>(null)

    var messageText by mutableStateOf("")
    var isVisibleMessage by mutableStateOf(false)

    suspend fun getImage(imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            try {
                val list = imageUrl.split("/")
                val bucket = list[0]
                val file = list[1]
                val byteArray = repository.getFileFromStorage(bucket, file)
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

    fun updateFavorite(isFavoritePub: Boolean, idPublication: String) {
        viewModelScope.launch {
            try {
                if (isFavoritePub) {
                    repository.deleteFromFavorite(idPublication)
                } else {
                    repository.updateFavorite(idPublication)
                }
                isFavorite = !isFavorite
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

    fun checkFavorite(idPublication: String) {
        viewModelScope.launch {
            try {
                val data = repository.findFavorite(idPublication)
                isFavorite = data != null
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
}