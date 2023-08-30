package ru.demchuk.messenger.ui.people.elm

import ru.demchuk.messenger.domain.useCase.people.model.UserModelUseCase
import ru.demchuk.messenger.ui.people.model.UserModelUi

data class State(
    val progressBarShow: Boolean = false,
    val errorShow: Boolean = false,
    val recyclerViewShow: Boolean = false,
    val listUsers: List<UserModelUi>? = null,
)

sealed class Event {

    sealed class Ui : Event() {
        object Init : Ui()
    }

    sealed class Internal : Event() {
        class LoadedUsers(val listUsers : List<UserModelUi>) : Internal()
        data class ErrorLoading(val error: Throwable) : Internal()
    }
}

sealed class Command() {
    object LoadUsers : Command()
}

sealed class Effect {
    data class NextPageLoadError(val error: Throwable) : Effect()

}
