package ru.demchuk.messenger.ui.recyclerStreams.elm

import kotlinx.coroutines.flow.*
import vivid.money.elmslie.core.switcher.Switcher
import vivid.money.elmslie.coroutines.Actor
import vivid.money.elmslie.coroutines.switch

object Actor : Actor<Command, Event> {

    private val switcher = Switcher()
    private val sharedFlow : MutableSharedFlow<String> = MutableSharedFlow()

    override fun execute(command: Command): Flow<Event> = when (command) {
        is Command.LoadStreams -> switcher.switch { command.userRequestUseCase.send() }
            .mapEvents({ list -> Event.Internal.StreamsLoaded(list) },
                { error -> Event.Internal.ErrorLoading(error) })
    }

    private fun st() = sharedFlow
}