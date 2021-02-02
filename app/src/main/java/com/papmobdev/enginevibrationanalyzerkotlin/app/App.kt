package com.papmobdev.enginevibrationanalyzerkotlin.app

import android.app.Application
import com.papmobdev.enginevibrationanalyzerkotlin.app.koin.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(KoinModules.all)
        }
    }
}