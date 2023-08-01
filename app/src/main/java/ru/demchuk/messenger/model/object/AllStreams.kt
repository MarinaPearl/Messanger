package ru.demchuk.messenger.model.`object`

import com.google.gson.annotations.SerializedName


data class AllStreams(
    @SerializedName("streams")
    var listStreams: List<Streams>
)