package ru.demchuk.messenger.di

import ru.demchuk.messenger.data.repository.UserRequestAllStreams
import ru.demchuk.messenger.data.repository.UserRequestSubscribedStreams
import ru.demchuk.messenger.data.storage.zulipApi.ApiStorageStreams
import ru.demchuk.messenger.data.storage.zulipApi.ZulipAllStreams
import ru.demchuk.messenger.data.storage.zulipApi.ZulipSubscribedStreams
import ru.demchuk.messenger.ui.recyclerStreams.elm.Actor
import ru.demchuk.messenger.ui.recyclerStreams.elm.StoreFactory
import ru.demchuk.messenger.domain.repository.UserRequestStreamsRepository
import ru.demchuk.messenger.domain.useCase.streams.UserRequestSearchStreamsUseCase
import ru.demchuk.messenger.domain.useCase.streams.UserRequestStreamsUseCase
import ru.demchuk.messenger.domain.useCase.streams.UserRequestUseCase

class GlobalDi private constructor() {

    private val requestAllStreamDataBase: ApiStorageStreams by lazy { ZulipAllStreams() }

    private val requestSubscribedStreamDataBase: ApiStorageStreams by lazy { ZulipSubscribedStreams() }

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

    private val requestAllStreamsUseCase: UserRequestUseCase by lazy {
        UserRequestStreamsUseCase(
            requestAllStreamsRepository
        )
    }

    private val requestSubscribedStreamsUseCase: UserRequestUseCase by lazy {
        UserRequestStreamsUseCase(
            requestSubscribedStreamsRepository
        )
    }

    private val requestSearchSubscribedStreamsUseCase by lazy {
        UserRequestSearchStreamsUseCase(
            requestSubscribedStreamsRepository
        )
    }

    private val requestSearchAllStreamsUseCase by lazy {
        UserRequestSearchStreamsUseCase(
            requestAllStreamsRepository
        )
    }

    private val actor by lazy {
        Actor(
            requestAllStreamsUseCase,
            requestSubscribedStreamsUseCase,
            requestSearchSubscribedStreamsUseCase,
            requestSearchAllStreamsUseCase
        )
    }


    val storeFactory by lazy { StoreFactory(actor) }


    companion object {

        lateinit var INSTANCE: GlobalDi

        fun init() {
            INSTANCE = GlobalDi()
        }
    }

}