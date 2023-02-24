package ru.coralcode.yrn

import android.app.Application
import android.content.Context
import ru.coralcode.yrn.data.Storage

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Storage.start(assets)
    }
}