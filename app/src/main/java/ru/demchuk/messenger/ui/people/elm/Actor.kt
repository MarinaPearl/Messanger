package ru.demchuk.messenger.ui.people.elm
import kotlinx.coroutines.flow.Flow
import vivid.money.elmslie.coroutines.Actor

class Actor : Actor<Command, Event>{

    override fun execute(command: Command): Flow<Event> {
        TODO("Not yet implemented")
    }
}