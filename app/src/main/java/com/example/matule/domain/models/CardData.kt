package com.example.matule.domain.models

data class CardData(
    val publicationId: String = "",
    val name: String = "",
    val image: String? = null,
    var isFavorite: Boolean = false,
    val publicationData: String = "",
    val isReading: Boolean = false,
    val countPageReading: Int = 0
)
