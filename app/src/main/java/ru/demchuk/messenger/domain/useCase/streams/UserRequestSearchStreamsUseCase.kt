package ru.demchuk.messenger.domain.useCase.streams

import ru.demchuk.messenger.domain.repository.UserRequestStreamsRepository
import ru.demchuk.messenger.domain.useCase.streams.model.StreamModelUseCase

class UserRequestSearchStreamsUseCase(
    private val userRequestRepository: UserRequestStreamsRepository
) {
    suspend fun send(query: String): List<StreamModelUseCase> {
        val resultList = userRequestRepository.receiveAnswerOnRequest()
        return filterList(resultList, query)

    }

    private fun filterList(
        list: List<StreamModelUseCase>,
        query: String
    ): List<StreamModelUseCase> {
        val listResultFilter = mutableListOf<StreamModelUseCase>()
        list.forEach {
            val regex = query.toRegex(RegexOption.IGNORE_CASE)
            if (regex.containsMatchIn(it.name)) {
                listResultFilter.add(it)
            }
        }
        return if (listResultFilter.size > 0) {
            listResultFilter
        } else list
    }
}