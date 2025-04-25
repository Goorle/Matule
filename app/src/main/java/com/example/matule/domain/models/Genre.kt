package com.example.matule.domain.models

import kotlinx.serialization.SerialName

data class Genre(
    @SerialName("genre_id")
    val genreId: String = "",
    val name: String = ""
)
