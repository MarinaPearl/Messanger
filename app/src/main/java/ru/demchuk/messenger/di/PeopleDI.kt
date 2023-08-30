package ru.demchuk.messenger.di

import ru.demchuk.messenger.data.repository.UserRequestPeople
import ru.demchuk.messenger.data.storage.zulipApi.ZulipPeople
import ru.demchuk.messenger.domain.useCase.people.UserRequestPeopleUseCase
import ru.demchuk.messenger.ui.people.elm.Actor
import ru.demchuk.messenger.ui.people.elm.StoreFactory

class PeopleDI private constructor() {

    private val peopleApiStorage by lazy {ZulipPeople()}
    private val peopleRepository by lazy {UserRequestPeople(peopleApiStorage)}
    private val peopleUseCase by lazy {UserRequestPeopleUseCase(peopleRepository)}
    private val actor: Actor by lazy { Actor(peopleUseCase) }
    val storeFactory: StoreFactory by lazy { StoreFactory(actor) }

    companion object {

        lateinit var INSTANCE: PeopleDI

        fun init() {
            INSTANCE = PeopleDI()
        }
    }
}