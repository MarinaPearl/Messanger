package ru.demchuk.messenger.data.api

import retrofit2.http.GET
import ru.demchuk.messenger.data.model.People

interface PeopleGetterZulipApi {
    @GET("users")
    suspend fun getPeople() : People
}