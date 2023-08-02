package ru.demchuk.messenger.data.`object`

import kotlinx.serialization.Serializable

@Serializable
data class Streams(
    var stream_id: Int,
    var name: String
)
