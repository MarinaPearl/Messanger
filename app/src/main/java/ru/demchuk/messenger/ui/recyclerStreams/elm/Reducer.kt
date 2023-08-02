package ru.demchuk.messenger.ui.recyclerStreams.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

object Reducer : DslReducer<Event, State, Effect, Command>() {

    override fun Result.reduce(event: Event) = when (event) {
        is Event.Ui.LoadingStreams -> {
            state { copy(progressBarShow = true, errorShow = false, recyclerViewShow = false) }
            commands { +Command.LoadStreams(event.userRequestUseCase) }
        }
        is Event.Internal.StreamsLoaded -> {
            state { copy(progressBarShow = false, errorShow = false, recyclerViewShow = true, listStreams = event.items) }
        }
        is Event.Internal.ErrorLoading -> {
            state { copy(progressBarShow = false, errorShow = true, recyclerViewShow = false) }
        }
        else -> {}
    }
}