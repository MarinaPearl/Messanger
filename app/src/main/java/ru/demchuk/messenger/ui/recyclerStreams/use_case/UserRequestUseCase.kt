package ru.demchuk.messenger.ui.recyclerStreams.use_case

import kotlinx.coroutines.flow.Flow
import ru.demchuk.messenger.ui.recyclerStreams.use_case.model.StreamModelUseCase

interface UserRequestUseCase {
    fun send() : Flow<List<StreamModelUseCase>>
}