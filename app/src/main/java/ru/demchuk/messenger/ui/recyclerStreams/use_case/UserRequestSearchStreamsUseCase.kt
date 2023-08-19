package ru.demchuk.messenger.ui.recyclerStreams.use_case

import ru.demchuk.messenger.ui.recyclerStreams.repository.UserRequestStreamsRepository
import ru.demchuk.messenger.ui.recyclerStreams.use_case.model.StreamModelUseCase

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