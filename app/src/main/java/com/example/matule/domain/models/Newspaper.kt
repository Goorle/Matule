package com.example.matule.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Newspaper(
    @SerialName("newspaper_id")
    val newspaperId: String = "",
    @SerialName("publication_id")
    val publicationId: String = "",
    @SerialName("created_at")
    val createdAt: String = ""
)