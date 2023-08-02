package ru.demchuk.messenger.ui.recyclerStreams.elm

import ru.demchuk.messenger.data.repository.UserRequestSubscribedStreams
import ru.demchuk.messenger.ui.recyclerStreams.repository.UserRequestRepository
import ru.demchuk.messenger.ui.recyclerStreams.use_case.UseCaseUserRequestAllSubscribed
import ru.demchuk.messenger.ui.recyclerStreams.use_case.UserRequestUseCase
import vivid.money.elmslie.coroutines.ElmStoreCompat

object StoreFactory{

    private val repoSubscribedStreams : UserRequestRepository = UserRequestSubscribedStreams()
    private val useCaseSubscribedStreams : UserRequestUseCase = UseCaseUserRequestAllSubscribed(
        repoSubscribedStreams)
    private val store by lazy {
        ElmStoreCompat(
            initialState = State(),
            reducer = Reducer,
            actor = Actor(useCaseSubscribedStreams)
        )
    }

    fun provide() = store
}
