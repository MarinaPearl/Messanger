package ru.demchuk.messenger.data.api

import retrofit2.http.GET
import ru.demchuk.messenger.data.`object`.AllStreams

interface GetStreamsZulipApi {
    @GET("streams")
    suspend fun getAllStreams() : AllStreams
}