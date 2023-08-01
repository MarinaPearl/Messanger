package ru.demchuk.messenger.ui.recyclerStreams.vm

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

suspend fun <T1> loadStreams(
    s1: suspend CoroutineScope.() -> T1
) : T1 {
    return coroutineScope {
        val result = async(block = s1)
        result.await()
    }
}