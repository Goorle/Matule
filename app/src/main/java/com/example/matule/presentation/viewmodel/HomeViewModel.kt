package com.example.matule.presentation.viewmodel

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matule.data.Repositories
import com.example.matule.domain.models.NewspaperResponse
import com.example.matule.domain.models.UserCollection
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.exception.PostgrestRestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val repository = Repositories()
    private var newspaper by mutableStateOf<List<NewspaperResponse>>(listOf())
    private var userPublication by mutableStateOf<List<UserCollection>>(listOf())
    var isLoading by mutableStateOf(false)

    var messageText by mutableStateOf("")
    var isVisibleMessage by mutableStateOf(false)
    var isVisibleMessageError by mutableStateOf(false)

    init {
        updateNewsPaper()
    }

    fun updateNewsPaper() {

        viewModelScope.launch {
            isLoading = true
            try {

            val userId = repository.getUserId()
            if (userId.isNotEmpty()) {
                userPublication = repository.getPublicationUser(userId)
            }
            newspaper = repository.getNewsPapersWithDetails()

            setUserNewspaper()
            } catch (_: PostgrestRestException) {
                messageText = "Сервер временно недоступен. Попробуйте повторить попытку позже"
                isVisibleMessage = true
            } catch (_: HttpRequestTimeoutException) {
                messageText = "Не удалось соединиться с сервером. Проверьте ваше интернет-соединение"
                isVisibleMessage = true
            } catch(_: HttpRequestException) {
                messageText = "Проблемы с соединением. Проверьте ваше подключение к интернету"
                isVisibleMessage = true
            } finally {
                isLoading = false
            }
        }
    }

    fun setUserNewspaper() {
        try {
            newspaper.forEach { newspaper ->
                userPublication.forEach { publication ->
                    if (newspaper.publication != null)
                    if (newspaper.publication.id == publication.publicationId) {
                        newspaper.userCollection = publication
                    }
                }
            }
        } catch (e: Exception) {
            messageText = "${e.message}"
            isVisibleMessageError = true
        }
    }

    fun getNewsPaper(): List<NewspaperResponse> {
        return newspaper
    }
}