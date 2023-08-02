package ru.demchuk.messenger.ui.recyclerStreams.elm

import vivid.money.elmslie.coroutines.ElmStoreCompat

object StoreFactory{

    private val store by lazy {
        ElmStoreCompat(
            initialState = State(),
            reducer = Reducer,
            actor = Actor
        )
    }
    fun provide() = store
}
