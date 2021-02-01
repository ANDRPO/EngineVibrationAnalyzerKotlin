package com.papmobdev.enginevibrationanalyzerkotlin.app.koin

import com.papmobdev.data.AppDataBase
import com.papmobdev.data.AppDataBaseInstance
import com.papmobdev.data.CarsDataSourceImpl
import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.usecasecargeneration.GetGenerationsUseCase
import com.papmobdev.domain.cars.usecasecargeneration.GetGenerationsUseCaseImpl
import com.papmobdev.domain.cars.usecasecarmarks.GetMarksUseCase
import com.papmobdev.domain.cars.usecasecarmarks.GetMarksUseCaseImpl
import com.papmobdev.domain.cars.usecasecarmodels.GetModelsUseCase
import com.papmobdev.domain.cars.usecasecarmodels.GetModelsUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@ExperimentalCoroutinesApi
object KoinModules {

    private val carsDataBaseModule = module {
        single { AppDataBaseInstance.build(androidContext()) }
        single { get<AppDataBase>().carsDao() }
    }

    private val dataSourceModule = module {
        factory<CarsDataSource> {
            CarsDataSourceImpl(get())
        }
    }

    private val useCaseModule = module {
        factory<GetMarksUseCase> {
            GetMarksUseCaseImpl(
                get()
            )
        }
        factory<GetModelsUseCase> {
            GetModelsUseCaseImpl(
                get()
            )
        }
        factory<GetGenerationsUseCase> {
            GetGenerationsUseCaseImpl(
                get()
            )
        }
    }

    private val baseAppModule = module {
        loadKoinModules(
            listOf(
                carsDataBaseModule,
                dataSourceModule,
                useCaseModule
            )
        )
    }

    val all = listOf(
        baseAppModule,
        presentationModule
    )
}