package com.example.matule.domain.models

import kotlinx.serialization.SerialName

data class FavoritePublication(
    @SerialName("favorite_id")
    val favoriteId: String = "",
    @SerialName("user_id")
    val userId: String = "",
    @SerialName("publication_id")
    val publicationId: String = "",
    @SerialName("created_at")
    val createdAt: String = ""
)
