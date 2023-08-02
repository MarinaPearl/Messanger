package ru.demchuk.messenger.ui.recyclerStreams.elm

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.demchuk.messenger.data.ConnectWithApi
import vivid.money.elmslie.core.switcher.Switcher
import vivid.money.elmslie.coroutines.Actor
import vivid.money.elmslie.coroutines.switch

object Actor : Actor<Command, Event> {

    private val switcher = Switcher()

    override fun execute(command: Command): Flow<Event> = when (command) {
        is Command.LoadStreams -> switcher.switch { secondsFlow() }
            .mapEvents({ list -> Event.Internal.StreamsLoaded(list) },
                { error -> Event.Internal.ErrorLoading(error) })
    }


    private fun secondsFlow() = flow {
        val result = ConnectWithApi.mainApi.getAllStreams()
        val listStream = result.listStreams
        emit(listStream)
    }

}