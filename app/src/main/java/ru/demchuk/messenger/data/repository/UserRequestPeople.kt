package ru.demchuk.messenger.data.repository

import ru.demchuk.messenger.data.storage.zulipApi.ApiStoragePeople
import ru.demchuk.messenger.domain.repository.UserRequestPeopleRepository
import ru.demchuk.messenger.domain.useCase.people.model.UserModelUseCase

class UserRequestPeople(private val apiStorage : ApiStoragePeople) : UserRequestPeopleRepository {

    override suspend fun receiveAnswerOnRequest(): List<UserModelUseCase> {
        return apiStorage.receiveListPeople().toListPeopleByDomain()
    }
}