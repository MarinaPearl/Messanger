package ru.demchuk.messenger.data.api

import retrofit2.http.GET
import ru.demchuk.messenger.data.model.Users

interface GetUsersZulipApi {
    @GET("users")
    suspend fun getAllUsers() : Users
}