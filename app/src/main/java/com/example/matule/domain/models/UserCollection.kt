package com.example.matule.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCollection(
    @SerialName("collection_id")
    val collectionId: String = "",
    @SerialName("publication_id")
    val publicationId: String = "",
    @SerialName("user_id")
    val userId: String = "",
    @SerialName("is_reading")
    var isReading: Boolean = false,
    @SerialName("added_at")
    val addedAt: String = "",
    @SerialName("count_reading")
    var countReading: Int = 0
)
