package ru.demchuk.messenger.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Stream(
    var stream_id: Int,
    var name: String
)
