package com.example.matule.domain.models

import kotlinx.serialization.SerialName

data class Newspaper(
    @SerialName("newspaper_id")
    val newspaperId: String = "",
    @SerialName("publication_id")
    val publicationId: String = "",
    @SerialName("created_at")
    val createdAt: String = ""
)