package com.papmobdev.enginevibrationanalyzerkotlin.app.koin

import com.papmobdev.enginevibrationanalyzerkotlin.data.CarsDataBase
import com.papmobdev.enginevibrationanalyzerkotlin.data.CarsDataBaseInstance
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object KoinModules {

    private val dataBaseModule = module {
        single { CarsDataBaseInstance.build(androidContext()) }
        single {get<CarsDataBase>().carsDao()}
    }

    private val baseAppModule = module {
        loadKoinModules(
                listOf(
                        dataBaseModule
                )
        )
    }

    val all = listOf(
            baseAppModule
    )
}