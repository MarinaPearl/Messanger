package ru.demchuk.messenger

import android.app.Application
import ru.demchuk.messenger.di.PeopleDI
import ru.demchuk.messenger.di.StreamsDI

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        StreamsDI.init()
        PeopleDI.init()
    }
}