package ru.demchuk.messenger.ui.people.elm

import kotlinx.coroutines.flow.Flow
import ru.demchuk.messenger.domain.useCase.people.UserRequestPeopleUseCase
import vivid.money.elmslie.core.switcher.Switcher
import vivid.money.elmslie.coroutines.Actor
import vivid.money.elmslie.coroutines.switch

class Actor(private val useCase: UserRequestPeopleUseCase) : Actor<Command, Event> {

    private val switcher = Switcher()
    override fun execute(command: Command): Flow<Event> = when (command) {
        is Command.LoadUsers -> switcher.switch { useCase.send() }
            .mapEvents({ list -> Event.Internal.LoadedUsers(list.toListUsersByUi()) },
                { error -> Event.Internal.ErrorLoading(error) })
    }
}