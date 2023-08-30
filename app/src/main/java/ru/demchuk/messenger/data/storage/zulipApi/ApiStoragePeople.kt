package ru.demchuk.messenger.data.storage.zulipApi

import ru.demchuk.messenger.data.model.User

interface ApiStoragePeople {

    suspend fun  receiveListPeople() : List<User>
}