package ru.demchuk.messenger.data.repository

import ru.demchuk.messenger.data.storage.zulipApi.ApiStorageUsers
import ru.demchuk.messenger.domain.repository.UserRequestUsersRepository
import ru.demchuk.messenger.domain.useCase.people.model.UserModel

class UserRequestUsers(private val apiStorage : ApiStorageUsers) : UserRequestUsersRepository {

    override suspend fun receiveAnswerOnRequest(): List<UserModel> {
        apiStorage.receiveListUsers()
    }
}