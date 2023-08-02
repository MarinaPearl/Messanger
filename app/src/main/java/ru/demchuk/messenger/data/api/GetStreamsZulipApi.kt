package ru.demchuk.messenger.data.api

import retrofit2.http.GET
import ru.demchuk.messenger.data.model.AllStreams
import ru.demchuk.messenger.data.model.SubscriptionStreams

interface GetStreamsZulipApi {
    @GET("streams")
    suspend fun getAllStreams() : AllStreams

    @GET("users/me/subscriptions")
    suspend fun getSubscriptionsStreams() : SubscriptionStreams
}