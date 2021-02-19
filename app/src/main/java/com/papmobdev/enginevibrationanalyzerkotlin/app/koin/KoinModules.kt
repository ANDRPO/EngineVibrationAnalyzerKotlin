package com.papmobdev.enginevibrationanalyzerkotlin.app.koin

import com.papmobdev.data.database.AppDataBase
import com.papmobdev.data.database.AppDataBaseInstance
import com.papmobdev.data.database.CarsDataSourceImpl
import com.papmobdev.data.database.DiagnosticDataSourceImpl
import com.papmobdev.data.sensor.AppAccelerometer
import com.papmobdev.data.sensor.AppAccelerometerImpl
import com.papmobdev.data.sensor.SensorDataSourceImpl
import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.InteractorCars
import com.papmobdev.domain.cars.usecasecargeneration.GetGenerationsUseCase
import com.papmobdev.domain.cars.usecasecargeneration.GetGenerationsUseCaseImpl
import com.papmobdev.domain.cars.usecasecarmarks.GetMarksUseCase
import com.papmobdev.domain.cars.usecasecarmarks.GetMarksUseCaseImpl
import com.papmobdev.domain.cars.usecasecarmodels.GetModelsUseCase
import com.papmobdev.domain.cars.usecasecarmodels.GetModelsUseCaseImpl
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.ObserveConfigurationCarUseCase
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.ObserveConfigurationCarUseCaseImpl
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.UpdateConfigurationCarUseCase
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.UpdateConfigurationCarUseCaseImpl
import com.papmobdev.domain.cars.usecasetypesfuels.GetTypesFuelUseCase
import com.papmobdev.domain.cars.usecasetypesfuels.GetTypesFuelUseCaseImpl
import com.papmobdev.domain.cars.usecasevibrationsource.GetVibrationSourceUseCase
import com.papmobdev.domain.cars.usecasevibrationsource.GetVibrationSourceUseCaseImpl
import com.papmobdev.domain.diagnostic.DiagnosticDataSource
import com.papmobdev.domain.diagnostic.usecasediagnostic.SendDiagnosticUseCase
import com.papmobdev.domain.diagnostic.usecasediagnostic.SendDiagnosticUseCaseImpl
import com.papmobdev.domain.diagnostic.usecasesensorevents.SendListSensorEventsUseCase
import com.papmobdev.domain.diagnostic.usecasesensorevents.SendListSensorEventsUseCaseImpl
import com.papmobdev.domain.sensor.SensorDataSource
import com.papmobdev.domain.sensor.interactorsensor.InteractorSensor
import com.papmobdev.domain.sensor.interactorsensor.InteractorSensorImpl
import com.papmobdev.domain.sensor.usecaseobservesensor.ObserveSensorUseCase
import com.papmobdev.domain.sensor.usecaseobservesensor.ObserveSensorUseCaseImpl
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

    private val sensorModule = module {
        single<AppAccelerometer> { AppAccelerometerImpl(androidContext()) }
    }

    private val dataSourceModule = module {
        factory<CarsDataSource> {
            CarsDataSourceImpl(get())
        }
        single<SensorDataSource> {
            SensorDataSourceImpl(get())
        }
        factory<DiagnosticDataSource> {
            DiagnosticDataSourceImpl(get())
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
        factory<ObserveConfigurationCarUseCase> {
            ObserveConfigurationCarUseCaseImpl(
                get()
            )
        }
        factory<GetVibrationSourceUseCase> {
            GetVibrationSourceUseCaseImpl(
                get()
            )
        }
        factory<GetTypesFuelUseCase> {
            GetTypesFuelUseCaseImpl(
                get()
            )
        }
        factory<UpdateConfigurationCarUseCase> {
            UpdateConfigurationCarUseCaseImpl(
                get()
            )
        }
        factory<SendDiagnosticUseCase> {
            SendDiagnosticUseCaseImpl(
                get()
            )
        }
        factory<SendListSensorEventsUseCase> {
            SendListSensorEventsUseCaseImpl(
                get()
            )
        }
        single<ObserveSensorUseCase> {
            ObserveSensorUseCaseImpl(
                get()
            )
        }
    }

    private val interactorsModule = module {
        factory<InteractorSensor> {
            InteractorSensorImpl(
                get(), get()
            )
        }
    }

    private val baseAppModule = module {
        loadKoinModules(
            listOf(
                carsDataBaseModule,
                sensorModule,
                dataSourceModule,
                interactorsModule,
                useCaseModule
            )
        )
    }

    val all = listOf(
        baseAppModule,
        presentationModule
    )
}