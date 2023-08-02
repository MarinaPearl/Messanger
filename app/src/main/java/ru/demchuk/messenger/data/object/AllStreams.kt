package ru.demchuk.messenger.data.`object`

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllStreams(
    @SerialName("streams")
    var listStreams: List<Streams>
)