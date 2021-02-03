package com.papmobdev.enginevibrationanalyzerkotlin.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.papmobdev.enginevibrationanalyzerkotlin.app.koin.KoinModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(KoinModules.all)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}