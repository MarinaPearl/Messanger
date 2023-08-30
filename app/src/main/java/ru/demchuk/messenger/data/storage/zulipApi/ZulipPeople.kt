package ru.demchuk.messenger.data.storage.zulipApi

import ru.demchuk.messenger.data.Client
import ru.demchuk.messenger.data.model.User

class ZulipPeople : ApiStoragePeople {
    override suspend fun receiveListPeople(): List<User> {
        val result = Client.getUsers().getPeople()
        return result.users
    }
}