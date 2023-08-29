package ru.demchuk.messenger.data.storage.zulipApi

import ru.demchuk.messenger.data.model.Stream

interface ApiStorageStreams {
    suspend fun  receiveListStreams() : List<Stream>
}