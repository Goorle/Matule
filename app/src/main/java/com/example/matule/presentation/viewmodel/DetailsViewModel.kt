package com.example.matule.presentation.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.matule.data.Repositories
import com.example.matule.domain.models.NewspaperResponse
import com.example.matule.domain.models.Publication
import com.example.matule.domain.models.UserCollection
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.postgrest.exception.PostgrestRestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.launch

class DetailsViewModel: ViewModel() {
    val repository = Repositories()
    var item by mutableStateOf<Publication?>(null)
    var bitmap by mutableStateOf<Bitmap?>(null)
    var linesDescription by mutableIntStateOf(3)
    var visiblePDF by mutableStateOf(false)
    var urlPDF by mutableStateOf("")
    var buttonEnable by mutableStateOf(false)
    var userCollection by mutableStateOf<UserCollection?>(null)

    var messageText by mutableStateOf("")
    var isVisibleMessage by mutableStateOf(false)

    fun getUserCollection(publicationId: String) {
        var userId = repository.getUserId()
        if (userId.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    userCollection = repository.getUserPublication(publicationId, userId)
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

    fun getEmptyCollection(itemId: String): UserCollection {
        var userId = repository.getUserId()
        if (userId.isNotEmpty()) {
            return UserCollection(
                publicationId = itemId,
                userId = userId,
                countReading = 1
            )
        }

        return UserCollection()
    }

    fun getItem(publicationId: String) {
        viewModelScope.launch {
            try {
                item = repository.getPublicationById(publicationId)
                if (item != null) {
                    buttonEnable = true
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
    }

    fun getImage() {
        viewModelScope.launch {
            try {
                val itemBuff = item
                if (itemBuff != null) {
                    val imageUrl = itemBuff.image
                    val list = imageUrl.split("/")
                    val bucket = list[0]
                    val file = list[1]
                    val byteArray = repository.getFileFromStorage(bucket, file)
                    bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
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
    }

    fun getUrlPDF() {
        val itemBuff = item
        if (itemBuff != null && itemBuff.pointFile.isNotEmpty()) {
            try {
                val filePath = itemBuff.pointFile
                val list = filePath.split("/")
                val bucket = list[0]
                val file = list[1]

                urlPDF = repository.getUrlFile(bucket, file)
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

    fun changeLines() {
        if (linesDescription == 3) {
            linesDescription = Int.MAX_VALUE
        } else {
            linesDescription = 3
        }
    }

    fun addPublicationToCollection(publicationId: String) {
        viewModelScope.launch {
            try {
                val userId = repository.getUserId()

                if (userId != "" && userCollection == null) {
                    repository.addPublicationCollection(publicationId, userId)
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
    }
}