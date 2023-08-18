package ru.demchuk.messenger.ui.recyclerStreams.elm

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.demchuk.messenger.ui.exception.runCatchingNonCancellation
import ru.demchuk.messenger.ui.recyclerStreams.use_case.UserRequestUseCase
import ru.demchuk.messenger.ui.recyclerStreams.vm.loadStreams
import ru.demchuk.messenger.ui.state.ScreenState
import ru.demchuk.messenger.use_case.LoadStreams
import vivid.money.elmslie.core.switcher.Switcher
import vivid.money.elmslie.coroutines.Actor
import vivid.money.elmslie.coroutines.switch
import java.io.StringReader

class Actor(
    private val userRequestAllStreamsUseCase: UserRequestUseCase,
    private val userRequestSubscribedStreamsUseCase: UserRequestUseCase,
) : Actor<Command, Event> {


    private val switcher = Switcher()
    private val shared: shared by lazy { shared() }

    override fun execute(command: Command): Flow<Event> = when (command) {
        is Command.LoadAllStreams -> switcher.switch { userRequestAllStreamsUseCase.send() }
            .mapEvents({ list -> Event.Internal.StreamsLoaded(list) },
                { error -> Event.Internal.ErrorLoading(error) })

        is Command.LoadSubscribedStreams -> switcher.switch { userRequestSubscribedStreamsUseCase.send() }
            .mapEvents({ list -> Event.Internal.StreamsLoaded(list) },
                { error -> Event.Internal.ErrorLoading(error) })

        is Command.Init -> shared.open().mapEvents({ Event.Internal.Init },
            { error -> Event.Internal.ErrorLoading(error) })

        is Command.SearchStream -> switcher.switch { shared.o(command.query) }
            .mapEvents({ Event.Internal.Init },
                { error -> Event.Internal.ErrorLoading(error) })

    }


}

class shared() {
    val sharedFlow: MutableSharedFlow<String> by lazy { MutableSharedFlow() }


   fun open() : Flow<Unit>{
        return sharedFlow
            .onEach { Log.d("nnnnnnnnnnnnnnnnnnnnn", it) }
            .filter { it.isEmpty() }
            .debounce(500L)
            .onEach { println(it) }
            .distinctUntilChanged()
            .flatMapLatest { flow { emit(op(it)) } }
    }

    fun o(s: String): Flow<Unit> {
          return flow {
              Log.d("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", s)
              sharedFlow.emit(s)
          }

    }
     fun op(s : String) {
         Log.d("aaaaaaaaaaaaaaa", s)
     }

}