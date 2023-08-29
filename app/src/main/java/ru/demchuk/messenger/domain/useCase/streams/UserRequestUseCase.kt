package ru.demchuk.messenger.domain.useCase.streams

import kotlinx.coroutines.flow.Flow
import ru.demchuk.messenger.domain.useCase.streams.model.StreamModelUseCase

interface UserRequestUseCase {
    fun send() : Flow<List<StreamModelUseCase>>
}