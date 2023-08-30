package ru.demchuk.messenger.data.repository

import ru.demchuk.messenger.data.model.User
import ru.demchuk.messenger.domain.useCase.people.model.UserModelUseCase

fun List<User>.toListPeopleByDomain(): List<UserModelUseCase> {
    val listUserModelUseCase = mutableListOf<UserModelUseCase>()
    this.forEach {
        val newUserModelUseCase = UserModelUseCase(
            id = it.id,
            email = it.email,
            name = it.name,
            active = it.active,
            avatar = it.avatar
        )
        listUserModelUseCase.add(newUserModelUseCase)
    }
    return listUserModelUseCase
}