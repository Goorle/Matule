package com.example.matule.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val id: String = "",
    @SerialName("user_id")
    var userId: String = "",
    val title: String = "",
    val text: String = "",
    @SerialName("is_reading")
    val isReading: Boolean = false,
    @SerialName("created_at")
    val createdAt: String = ""

)