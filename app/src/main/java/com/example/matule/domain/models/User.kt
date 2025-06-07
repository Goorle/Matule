package com.example.matule.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class User(
    @SerialName("user_id")
    val userId: String,
    val email: String = "",
    var phone: String = "",
    var firstname: String = "",
    var lastname: String? = "",
    @SerialName("second_name")
    var secondName: String = "",
    @SerialName("reg_date")
    val regDate: String = "",
    val subscription: Boolean = false,
    @SerialName("user_image")
    var userImage: String? = null
)