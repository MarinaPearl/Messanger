package ru.demchuk.messenger.di

import ru.demchuk.messenger.data.repository.UserRequestAllStreams
import ru.demchuk.messenger.data.repository.UserRequestSubscribedStreams
import ru.demchuk.messenger.data.storage.dataBase.DataBaseStorage
import ru.demchuk.messenger.data.storage.dataBase.ZulipAllStreams
import ru.demchuk.messenger.data.storage.dataBase.ZulipSubscribedStreams
import ru.demchuk.messenger.ui.recyclerStreams.elm.Actor
import ru.demchuk.messenger.ui.recyclerStreams.elm.StoreFactory
import ru.demchuk.messenger.ui.recyclerStreams.repository.UserRequestStreamsRepository
import ru.demchuk.messenger.ui.recyclerStreams.use_case.UserRequestStreamsUseCase
import ru.demchuk.messenger.ui.recyclerStreams.use_case.UserRequestUseCase

class GlobalDi private constructor() {

    private val requestAllStreamDataBase: DataBaseStorage by lazy { ZulipAllStreams() }

    private val requestSubscribedStreamDataBase: DataBaseStorage by lazy { ZulipSubscribedStreams() }

    private val requestAllStreamsRepository: UserRequestStreamsRepository by lazy {
        UserRequestAllStreams(
            requestAllStreamDataBase
        )
    }

    private val requestSubscribedStreamsRepository: UserRequestStreamsRepository by lazy {
        UserRequestSubscribedStreams(
            requestSubscribedStreamDataBase
        )
    }

    private val requestAllStreamsUseCase : UserRequestUseCase by lazy {UserRequestStreamsUseCase(requestAllStreamsRepository)}

    private val requestSubscribedStreamsUseCase : UserRequestUseCase by lazy {UserRequestStreamsUseCase(requestSubscribedStreamsRepository)}

    private val actor by lazy { Actor(requestAllStreamsUseCase, requestSubscribedStreamsUseCase) }


    val storeFactory by lazy {StoreFactory(actor)}


    companion object {

        lateinit var INSTANCE: GlobalDi

        fun init() {
            INSTANCE = GlobalDi()
        }
    }

}