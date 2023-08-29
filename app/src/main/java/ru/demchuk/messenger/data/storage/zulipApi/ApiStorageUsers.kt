package ru.demchuk.messenger.data.storage.zulipApi

import ru.demchuk.messenger.data.model.User

interface ApiStorageUsers {

    suspend fun  receiveListUsers() : List<User>
}