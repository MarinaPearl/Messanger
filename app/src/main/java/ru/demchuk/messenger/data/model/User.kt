package ru.demchuk.messenger.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("user_id")
    val id: Int,

    @SerialName("email")
    val email: String,

    @SerialName("full_name")
    val name: String,

    @SerialName("is_active")
    val active: Boolean,

    @SerialName("avatar_url")
    val avatar: String?
)