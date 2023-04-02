package ru.coralcode.yrn

import android.app.Application
import ru.coralcode.yrn.data.QuestionsRepository

class App: Application() {

    override fun onCreate() {
        QuestionsRepository.initialize(appContext = this)
        super.onCreate()
    }
}