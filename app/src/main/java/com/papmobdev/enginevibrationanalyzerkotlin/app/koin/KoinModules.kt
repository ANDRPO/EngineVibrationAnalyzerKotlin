package com.papmobdev.enginevibrationanalyzerkotlin.app.koin

import com.papmobdev.enginevibrationanalyzerkotlin.data.AppDataBase
import com.papmobdev.enginevibrationanalyzerkotlin.data.AppDataBaseInstance
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object KoinModules {

    private val carsDataBaseModule = module {
        single { AppDataBaseInstance.build(androidContext()) }
        single { get<AppDataBase>().carsDao() }
    }

    private val baseAppModule = module {
        loadKoinModules(
                listOf(
                        carsDataBaseModule
                )
        )
    }

    val all = listOf(
            baseAppModule
    )
}