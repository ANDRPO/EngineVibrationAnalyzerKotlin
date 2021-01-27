package com.papmobdev.enginevibrationanalyzerkotlin.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin(){
        startKoin{
            androidContext(this@App)
            //init modules
        }
    }


}