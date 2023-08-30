package ru.demchuk.messenger.domain.repository

import ru.demchuk.messenger.domain.useCase.people.model.UserModelUseCase

interface UserRequestPeopleRepository {
    suspend fun receiveAnswerOnRequest() : List<UserModelUseCase>
}