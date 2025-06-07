package com.example.matule.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewspaperResponse(
    @SerialName("newspaper_id")
    val newspaperId: String,

    @SerialName("Publication")
    val publication: PublicationData? = null,

    var userCollection: UserCollection? = null,

    var isFavorite: FavoritePublication? = null
)

@Serializable
data class PublicationData(
    val id: String = "",
    val title: String = "",
    @SerialName("publication_date")
    val publicationDate: String = "",
    val image: String? = "",
    val description: String = "",
    @SerialName("point_file")
    val pointFile: String = ""
)