package com.example.matule.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matule.data.Repositories
import com.example.matule.domain.models.Notification
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.postgrest.exception.PostgrestRestException
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.realtime
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.util.UUID

class NotificationViewModel: ViewModel() {
    private val repositories = Repositories()
    var text by mutableStateOf("")
    var notification by mutableStateOf<List<Notification>>(listOf())

    var messageText by mutableStateOf("")
    var isVisibleMessage by mutableStateOf(false)

    init {
        updateNotificationList()
    }

    fun updateNotificationList() {
        viewModelScope.launch {
            try {
                notification = repositories.getListNotification().reversed()
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


    suspend fun startRealtime(scope: CoroutineScope) {
        try {
            val channel = repositories.client.channel("notifications_${UUID.randomUUID()}")
            channel.postgresChangeFlow<PostgresAction.Insert>(schema = "public") {
                table = "Notification"
            }.onEach { change ->
                try {
                    updateNotificationList()
                } catch (e: Exception) {
                    Log.e("REALTIME_SUPABASE", "Decoding error", e)
                }
            }.launchIn(scope)
            channel.subscribe()

        } catch (e: Exception) {
            Log.e("REALTIME_SUPABASE", "Connection failed", e)
        }
    }

}