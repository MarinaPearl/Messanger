package ru.demchuk.messenger.domain.useCase.streams

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.demchuk.messenger.domain.repository.UserRequestStreamsRepository
import ru.demchuk.messenger.domain.useCase.streams.model.StreamModelUseCase

class UserRequestStreamsUseCase(private val userRequestRepository: UserRequestStreamsRepository) :
    UserRequestUseCase {

    override fun send(): Flow<List<StreamModelUseCase>> {
        return flow {
            emit(userRequestRepository.receiveAnswerOnRequest())
        }
    }
}