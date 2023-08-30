package ru.demchuk.messenger.data.repository

import ru.demchuk.messenger.data.storage.zulipApi.ApiStorageStreams
import ru.demchuk.messenger.domain.repository.UserRequestStreamsRepository
import ru.demchuk.messenger.domain.useCase.streams.model.StreamModelUseCase

class UserRequestSubscribedStreams(private val dataBaseStorage: ApiStorageStreams) :
    UserRequestStreamsRepository {
    override suspend fun receiveAnswerOnRequest(): List<StreamModelUseCase> {
        return dataBaseStorage.receiveListStreams().toListPeopleByDomain()
    }
}