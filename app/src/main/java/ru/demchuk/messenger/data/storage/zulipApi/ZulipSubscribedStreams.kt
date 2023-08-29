package ru.demchuk.messenger.data.storage.zulipApi

import ru.demchuk.messenger.data.Client
import ru.demchuk.messenger.data.model.Stream

class ZulipSubscribedStreams : ApiStorageStreams {

    override suspend fun receiveListStreams(): List<Stream> {
        val result = Client.getStreams().getSubscriptionsStreams()
        return result.subscriptions
    }
}