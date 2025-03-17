package com.example.matule.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Products(
    val id: String = "",
    val name: String = "",
    val cost: Double = 0.00,
    val category: String = "",
    @SerialName("created_at")
    val createdAt: String = "",
    val image: String = ""

)