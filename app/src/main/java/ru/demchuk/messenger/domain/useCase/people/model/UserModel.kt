package ru.demchuk.messenger.domain.useCase.people.model

import kotlinx.serialization.SerialName

data class UserModel(
    val id: Int,

    val email: String,

    val name: String,

    val active: Boolean,

    val avatar: String
)