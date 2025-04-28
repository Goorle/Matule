package com.example.matule.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Publication(
    val id: String = "",
    val title: String = "",
    @SerialName("publication_date")
    val publicationDate: String = "",
    val description: String = "",
    @SerialName("publication_type")
    val publicationType: String = "",
    val price: Float = 0.0f,
    @SerialName("point_file")
    val pointFile: String = "",
    @SerialName("created_at")
    val createdAt: String = "",
    val image: String = ""
)
