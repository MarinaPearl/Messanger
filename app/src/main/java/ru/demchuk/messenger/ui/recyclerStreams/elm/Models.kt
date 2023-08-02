package ru.demchuk.messenger.ui.recyclerStreams.elm

import android.os.Parcel
import android.os.Parcelable
import ru.demchuk.messenger.data.`object`.Streams


data class State(
    val shimmerShow : Boolean = false,
    val errorShow : Boolean = false,
    val recyclerViewShow : Boolean = false,
    val listStreams : List<Streams>? = null,
    )


sealed class Event {
    sealed class Ui : Event() {
        class SearchStreamsOnScreen(val query: String) : Ui()
        object LoadingAllStreams : Ui()
        object LoadingSubscribedStreams : Ui()
    }

    sealed class Internal : Event() {

        data class StreamsLoaded(val items: List<Streams>) : Internal()

        data class ErrorLoading(val error: Throwable) : Internal()
    }
}

sealed class Command {
    object LoadStreams : Command()
}

sealed class Effect {
    data class NextPageLoadError(val error: Throwable) : Effect()
}
