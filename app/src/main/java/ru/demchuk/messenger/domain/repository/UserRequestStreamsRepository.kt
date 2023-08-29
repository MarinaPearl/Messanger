package ru.demchuk.messenger.domain.repository

import ru.demchuk.messenger.domain.useCase.streams.model.StreamModelUseCase

interface UserRequestStreamsRepository {
    suspend fun receiveAnswerOnRequest() : List<StreamModelUseCase>
}