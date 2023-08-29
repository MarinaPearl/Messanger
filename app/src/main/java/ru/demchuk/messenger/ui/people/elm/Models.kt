package ru.demchuk.messenger.ui.people.elm

import ru.demchuk.messenger.ui.people.model.PeopleModel

data class State(
    val progressBarShow: Boolean = false,
    val errorShow: Boolean = false,
    val recyclerViewShow: Boolean = false,
    val listStreams: List<PeopleModel>? = null,
)

sealed class Event {

    sealed class Ui : Event() {
        object Init : Ui()
    }

    sealed class Internal : Event() {
        class LoadedUsers(val listUsers: List<PeopleModel>) : Internal()
    }
}

sealed class Command() {
    object LoadUsers : Command()
}

sealed class Effect {
    data class NextPageLoadError(val error: Throwable) : Effect()

}
