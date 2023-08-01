package ru.demchuk.messenger.model.api

import retrofit2.http.GET
import ru.demchuk.messenger.model.`object`.AllStreams

interface ZulipApi {
    @GET("streams")
    suspend fun getAllStreams() : AllStreams
}