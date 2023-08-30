package ru.demchuk.messenger.domain.useCase.people.model

data class UserModelUseCase(
    val id: Int,

    val email: String,

    val name: String,

    val active: Boolean,

    val avatar: String?
)