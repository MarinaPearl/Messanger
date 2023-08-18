package ru.demchuk.messenger.data.repository

import ru.demchuk.messenger.data.storage.dataBase.DataBaseStorage
import ru.demchuk.messenger.ui.recyclerStreams.repository.UserRequestStreamsRepository
import ru.demchuk.messenger.ui.recyclerStreams.use_case.model.StreamModelUseCase

class UserRequestSubscribedStreams(private val dataBaseStorage: DataBaseStorage) :
    UserRequestStreamsRepository {
    override suspend fun receiveAnswerOnRequest(): List<StreamModelUseCase> {
        return dataBaseStorage.receiveAnswerOnRequest().toListByDomain()
    }
}