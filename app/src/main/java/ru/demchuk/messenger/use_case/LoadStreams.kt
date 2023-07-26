package ru.demchuk.messenger.use_case

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.demchuk.messenger.stubStreamList
import ru.demchuk.messenger.ui.recyclerStreams.stream.Stream

object LoadStreams {

    suspend fun loadAllStreams(): List<Stream> = withContext(Dispatchers.IO) {
        delay(2000L)
        return@withContext stubStreamList
    }

    suspend fun loadStreamsForRequest(request: String): List<Stream> = withContext(Dispatchers.IO) {
        delay(2000L)
        return@withContext if (request.isNotEmpty()) {
            searchList(request)
        } else stubStreamList
    }

    private fun searchList(request: String): List<Stream> {
        val resulListSearch: MutableList<Stream> = mutableListOf()
        stubStreamList.forEach {
            val regex = request.toRegex(RegexOption.IGNORE_CASE)
            if (regex.containsMatchIn(it.name)) {
                resulListSearch.add(it)
            }
        }
        return resulListSearch
    }
}