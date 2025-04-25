package com.example.matule.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class User(
    @SerialName("user_id")
    val userId: String,
    val email: String = "",
    val phone: String = "",
    val firstname: String = "",
    val lastname: String = "",
    @SerialName("second_name")
    val secondName: String = "",
    @SerialName("reg_date")
    val regDate: String = ""
)