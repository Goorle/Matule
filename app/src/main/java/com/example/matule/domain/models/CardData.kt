package com.example.matule.domain.models

data class CardData(
    val publicationId: String = "",
    val name: String = "",
    val image: String? = null,
    var isFavorite: Boolean = false,
    var isInCart: Boolean = false,
    val publicationData: String = ""
)
