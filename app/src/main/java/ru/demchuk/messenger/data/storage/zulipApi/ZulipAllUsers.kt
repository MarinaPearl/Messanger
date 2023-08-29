package ru.demchuk.messenger.data.storage.zulipApi

import ru.demchuk.messenger.data.Client
import ru.demchuk.messenger.data.model.User

class ZulipAllUsers : ApiStorageUsers {
    override suspend fun receiveListUsers(): List<User> {
        val result = Client.getUsers().getAllUsers()
        return result.users
    }
}