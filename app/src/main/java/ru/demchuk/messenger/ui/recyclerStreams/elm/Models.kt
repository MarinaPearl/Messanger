package ru.demchuk.messenger.ui.recyclerStreams.elm

import ru.demchuk.messenger.ui.recyclerStreams.use_case.UserRequestUseCase
import ru.demchuk.messenger.ui.recyclerStreams.use_case.model.StreamModelUseCase


data class State(
    val shimmerShow : Boolean = false,
    val errorShow : Boolean = false,
    val recyclerViewShow : Boolean = false,
    val listStreams : List<StreamModelUseCase>? = null,
    )


sealed class Event {
    sealed class Ui : Event() {
        class SearchStreamsOnScreen(val query: String) : Ui()
        object LoadingAllStreams : Ui()
        class LoadingSubscribedStreams(val userRequestUseCase: UserRequestUseCase) : Ui()
    }

    sealed class Internal : Event() {

        data class StreamsLoaded(val items: List<StreamModelUseCase>) : Internal()

        data class ErrorLoading(val error: Throwable) : Internal()
    }
}

sealed class Command {
    class LoadStreams(val userRequestUseCase: UserRequestUseCase) : Command()
}

sealed class Effect {
    data class NextPageLoadError(val error: Throwable) : Effect()
}
