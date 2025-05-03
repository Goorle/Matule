package com.example.matule.domain.models

data class CardData(
    var publicationId: String = "",
    var name: String = "",
    var image: String? = null,
    var isFavorite: Boolean = false,
    var publicationData: String = "",
    var isReading: Boolean = false,
    var countPageReading: Int = 0,
    var maxCountPage: Int = 12
)
