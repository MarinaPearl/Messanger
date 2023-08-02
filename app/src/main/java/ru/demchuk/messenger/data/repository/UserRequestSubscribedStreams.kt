package ru.demchuk.messenger.data.repository

import ru.demchuk.messenger.data.Client
import ru.demchuk.messenger.data.model.Streams
import ru.demchuk.messenger.ui.recyclerStreams.repository.UserRequestRepository
import ru.demchuk.messenger.ui.recyclerStreams.use_case.model.StreamModelUseCase

class UserRequestSubscribedStreams : UserRequestRepository {

    override suspend fun receiveAnswerOnRequest(): List<StreamModelUseCase> {
        val result = Client.getRetrofitStreams().getAllStreams()
        return result.listStreams.toListByDomain()
    }

    private fun List<Streams>.toListByDomain(): List<StreamModelUseCase> {
        val listStreamDomain = mutableListOf<StreamModelUseCase>()
        this.forEach {
            val streamDomain = StreamModelUseCase(streamId = it.stream_id, name = it.name)
            listStreamDomain.add(streamDomain)
        }
        return listStreamDomain
    }
}