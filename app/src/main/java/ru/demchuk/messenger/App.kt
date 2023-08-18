package ru.demchuk.messenger

import android.app.Application
import ru.demchuk.messenger.di.GlobalDi

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalDi.init()
    }
}