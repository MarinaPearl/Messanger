package ru.demchuk.messenger.data.storage.dataBase

import ru.demchuk.messenger.data.model.Stream

interface DataBaseStorage {
    suspend fun  receiveAnswerOnRequest() : List<Stream>
}