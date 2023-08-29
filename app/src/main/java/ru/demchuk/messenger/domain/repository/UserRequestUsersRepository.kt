package ru.demchuk.messenger.domain.repository

import ru.demchuk.messenger.domain.useCase.people.model.UserModel

interface UserRequestUsersRepository {
    suspend fun receiveAnswerOnRequest() : List<UserModel>
}