package ru.demchuk.messenger.ui.people.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

object Reducer : DslReducer<Event, State, Effect, Command>() {

    override fun Result.reduce(event: Event) = when (event) {
        is Event.Ui.Init -> {
            state { copy(progressBarShow = true, errorShow = false, recyclerViewShow = false) }
            commands { +Command.LoadUsers }
        }
        is Event.Internal.LoadedUsers -> {
            state {
                copy(
                    progressBarShow = false,
                    errorShow = false,
                    recyclerViewShow = true,
                    listUsers = event.listUsers
                )
            }
        }
        is Event.Internal.ErrorLoading -> {
            state {
                copy(
                    progressBarShow = false,
                    errorShow = true,
                    recyclerViewShow = false,
                )
            }
        }
    }
}