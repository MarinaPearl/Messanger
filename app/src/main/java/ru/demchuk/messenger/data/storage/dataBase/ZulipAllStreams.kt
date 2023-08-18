package ru.demchuk.messenger.data.storage.dataBase

import ru.demchuk.messenger.data.Client
import ru.demchuk.messenger.data.model.Stream

class ZulipAllStreams : DataBaseStorage {

    override suspend fun receiveAnswerOnRequest(): List<Stream> {
        val result = Client.getRetrofitStreams().getAllStreams()
        return result.listStreams
    }
}