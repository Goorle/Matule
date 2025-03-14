package com.example.matule.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class User(
    val id: String,
    val name: String = "",
    @SerialName("second_name")
    val secondName: String = "",
    val phone: String = ""
)