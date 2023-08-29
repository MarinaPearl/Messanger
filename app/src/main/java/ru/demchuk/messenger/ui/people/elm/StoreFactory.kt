package ru.demchuk.messenger.ui.people.elm

import vivid.money.elmslie.coroutines.ElmStoreCompat

class StoreFactory(private val actor: Actor) {

    private val store by lazy {
        ElmStoreCompat(initialState = State(), reducer = Reducer, actor = actor)
    }

    fun provide() = store
}