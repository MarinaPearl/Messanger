package ru.demchuk.messenger.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Users(
    @SerialName("members")
    val users: List<User>
)