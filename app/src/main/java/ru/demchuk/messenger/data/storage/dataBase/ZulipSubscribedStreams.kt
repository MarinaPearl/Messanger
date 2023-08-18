package ru.demchuk.messenger.data.storage.dataBase

import ru.demchuk.messenger.data.Client
import ru.demchuk.messenger.data.model.Stream
import ru.demchuk.messenger.data.repository.toListByDomain

class ZulipSubscribedStreams : DataBaseStorage {

    override suspend fun receiveAnswerOnRequest(): List<Stream> {
        val result = Client.getRetrofitStreams().getSubscriptionsStreams()
        return result.subscriptions
    }
}