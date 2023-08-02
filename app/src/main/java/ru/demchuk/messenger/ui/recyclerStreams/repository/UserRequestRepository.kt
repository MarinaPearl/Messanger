package ru.demchuk.messenger.ui.recyclerStreams.repository

import ru.demchuk.messenger.ui.recyclerStreams.use_case.model.StreamModelUseCase

interface UserRequestRepository {
    suspend fun receiveAnswerOnRequest() : List<StreamModelUseCase>
}