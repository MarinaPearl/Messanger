package ru.demchuk.messenger.ui.people.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

object Reducer : DslReducer<Event, State, Effect, Command>() {

    override fun Result.reduce(event: Event): Any? {
        TODO("Not yet implemented")
    }
}