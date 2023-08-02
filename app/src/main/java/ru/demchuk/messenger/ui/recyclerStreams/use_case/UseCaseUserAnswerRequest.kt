package ru.demchuk.messenger.ui.recyclerStreams.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.demchuk.messenger.ui.recyclerStreams.repository.UserRequestRepository
import ru.demchuk.messenger.ui.recyclerStreams.use_case.model.StreamModelUseCase

class UseCaseUserAnswerRequest(private val userRequestRepository: UserRequestRepository) :
    UserRequestUseCase {

    override fun send(): Flow<List<StreamModelUseCase>> {
        return flow {
            emit(userRequestRepository.receiveAnswerOnRequest())
        }
    }
}