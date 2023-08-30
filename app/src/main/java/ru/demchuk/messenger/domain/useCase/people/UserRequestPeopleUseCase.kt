package ru.demchuk.messenger.domain.useCase.people

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.demchuk.messenger.domain.repository.UserRequestPeopleRepository
import ru.demchuk.messenger.domain.useCase.people.model.UserModelUseCase

class UserRequestPeopleUseCase(private val userRequestRepository: UserRequestPeopleRepository) {

    fun send(): Flow<List<UserModelUseCase>> {
        return flow {
            emit(userRequestRepository.receiveAnswerOnRequest())
        }
    }
}