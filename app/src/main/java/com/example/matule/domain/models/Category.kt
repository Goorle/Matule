package com.example.matule.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Category(
    val id: String = "",
    val name: String = "",
    @SerialName("created_at")
    val createdAt: String = ""
)