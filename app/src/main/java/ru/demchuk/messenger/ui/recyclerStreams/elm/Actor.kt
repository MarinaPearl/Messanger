package ru.demchuk.messenger.ui.recyclerStreams.elm

import kotlinx.coroutines.flow.*
import ru.demchuk.messenger.domain.useCase.streams.UserRequestSearchStreamsUseCase
import ru.demchuk.messenger.domain.useCase.streams.UserRequestUseCase
import ru.demchuk.messenger.domain.useCase.streams.model.StreamModelUseCase
import vivid.money.elmslie.core.switcher.Switcher
import vivid.money.elmslie.coroutines.Actor
import vivid.money.elmslie.coroutines.switch

class Actor(
    private val userRequestAllStreamsUseCase: UserRequestUseCase,
    private val userRequestSubscribedStreamsUseCase: UserRequestUseCase,
    private val userRequestSearchSubscribedStreamsUseCase: UserRequestSearchStreamsUseCase,
    private val userRequestSearchAllStreamsUseCase: UserRequestSearchStreamsUseCase,
) : Actor<Command, Event> {


    private val switcher = Switcher()
    private val stateFlow: MutableStateFlow<Boolean> by lazy { MutableStateFlow(true) }
    private val sharedFlow: MutableSharedFlow<String> by lazy { MutableSharedFlow() }

    override fun execute(command: Command): Flow<Event> = when (command) {
        is Command.LoadAllStreams -> switcher.switch { userRequestAllStreamsUseCase.send() }
            .mapEvents({ list -> Event.Internal.StreamsLoaded(list) },
                { error -> Event.Internal.ErrorLoading(error) })

        is Command.LoadSubscribedStreams -> switcher.switch { userRequestSubscribedStreamsUseCase.send() }
            .mapEvents({ list -> Event.Internal.StreamsLoaded(list) },
                { error -> Event.Internal.ErrorLoading(error) })

        is Command.Init -> init().mapEvents({ list -> Event.Internal.StreamsLoaded(list) },
            { error -> Event.Internal.ErrorLoading(error) })

        is Command.SearchStreams -> switcher.switch { send(command.query) }
            .mapEvents { error -> Event.Internal.ErrorLoading(error) }
        is Command.DefineActualOperation -> setFlagActualOperation(command.flagOperation)
            .mapEvents { error -> Event.Internal.ErrorLoading(error) }

    }

    private fun init(): Flow<List<StreamModelUseCase>> {
        return sharedFlow
            .debounce(500L)
            .onEach { println(it) }
            .flatMapLatest {
                flow {
                    if (stateFlow.value) {
                        emit(userRequestSearchSubscribedStreamsUseCase.send(it))
                    } else emit(userRequestSearchAllStreamsUseCase.send(it))
                }
            }
    }

    private fun send(query: String): Flow<Unit> {
        return flow {
            sharedFlow.emit(query)
        }
    }

    private fun setFlagActualOperation(flagOperation: Boolean): MutableStateFlow<Boolean> {
        stateFlow.value = flagOperation
        return stateFlow
    }
}