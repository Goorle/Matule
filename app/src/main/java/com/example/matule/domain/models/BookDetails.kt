package com.example.matule.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BookDetails(
    @SerialName("book_id")
    var bookId: String = "",
    var author: String = "",
    var image: String = "",
    var title: String = "",
    var genreName: String = ""
)
