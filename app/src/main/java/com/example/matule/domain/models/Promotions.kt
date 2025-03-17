package com.example.matule.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Promotions(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    @SerialName("created_at")
    val createdAt: String = ""
)