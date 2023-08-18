package ru.demchuk.messenger.ui.recyclerStreams.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.demchuk.messenger.ui.recyclerStreams.repository.UserRequestStreamsRepository
import ru.demchuk.messenger.ui.recyclerStreams.use_case.model.StreamModelUseCase

class UserRequestStreamsUseCase(private val userRequestRepository: UserRequestStreamsRepository) :
    UserRequestUseCase {

    override fun send(): Flow<List<StreamModelUseCase>> {
        return flow {
            emit(userRequestRepository.receiveAnswerOnRequest())
        }
    }
}