package ru.demchuk.messenger.data.repository

import ru.demchuk.messenger.data.model.Stream
import ru.demchuk.messenger.domain.useCase.streams.model.StreamModelUseCase

fun List<Stream>.toListByDomain(): List<StreamModelUseCase> {
    val listStreamDomain = mutableListOf<StreamModelUseCase>()
    this.forEach {
        val streamDomain = StreamModelUseCase(streamId = it.stream_id, name = it.name)
        listStreamDomain.add(streamDomain)
    }
    return listStreamDomain
}