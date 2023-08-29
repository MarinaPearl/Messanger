package ru.demchuk.messenger.data.storage.zulipApi

import ru.demchuk.messenger.data.Client
import ru.demchuk.messenger.data.model.Stream

class ZulipAllStreams : ApiStorageStreams {

    override suspend fun receiveListStreams(): List<Stream> {
        val result = Client.getStreams().getAllStreams()
        return result.listStreams
    }
}