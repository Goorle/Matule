package com.example.matule.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    @SerialName("book_id")
    val bookId: String = "",
    val author: String = "",
    @SerialName("page_count")
    val pageCount: String = "",
    @SerialName("genre_id")
    val genreId: String = "",
    @SerialName("publication_id")
    val publicationId: String = "",
    @SerialName("created_at")
    val createdAt: String = ""
)
