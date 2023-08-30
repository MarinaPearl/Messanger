package ru.demchuk.messenger.ui.people.elm

import ru.demchuk.messenger.domain.useCase.people.model.UserModelUseCase
import ru.demchuk.messenger.ui.people.model.UserModelUi

fun List<UserModelUseCase>.toListUsersByUi(): List<UserModelUi> {
    val listUsersUi = mutableListOf<UserModelUi>()
    this.forEach {
        val newUserModelUi = UserModelUi(
            id = it.id,
            name = it.name,
            active = it.active,
            email = it.email,
            avatar = it.avatar
        )
        listUsersUi.add(newUserModelUi)
    }
    return listUsersUi
}