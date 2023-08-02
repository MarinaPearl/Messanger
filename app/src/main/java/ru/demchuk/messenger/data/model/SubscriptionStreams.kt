package ru.demchuk.messenger.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionStreams(
    @SerialName("subscriptions")
    val subscriptions: List<Stream>
)
