package com.papmobdev.domain.diagnostic.interactor

import com.papmobdev.domain.cars.models.CarConfiguration
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.ObserveConfigurationCarUseCase
import com.papmobdev.domain.diagnostic.models.DiagnosticModel
import com.papmobdev.domain.diagnostic.usecasediagnostic.SendDiagnosticUseCase
import com.papmobdev.domain.diagnostic.usecasesensorevents.SendListSensorEventsUseCase
import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.util.*

@ExperimentalCoroutinesApi
class InteractorDiagnosticImpl(
    private val sendDiagnosticUseCase: SendDiagnosticUseCase,
    private val sendListSensorEventsUseCase: SendListSensorEventsUseCase,
    private val observeConfigurationCarUseCase: ObserveConfigurationCarUseCase
) : InteractorDiagnostic {
    override fun writeDiagnostic(listEvents: List<EventModel>): Flow<Result<Boolean>> = flow {
        getCarConfiguration()?.let { configuration ->
            sendDiagnosticUseCase(configuration.toDiagnosticModel()).firstOrNull()



        }
    }

    private suspend fun getCarConfiguration() = observeConfigurationCarUseCase().first().getOrNull()

    private fun CarConfiguration.toDiagnosticModel() = DiagnosticModel(
        idDiagnostic = null,
        dateTime = Date(),
        fkCarMark = fkCarMark,
        fkCarModel = fkCarModel,
        fkCarGeneration = fkCarGeneration,
        fkTypeFuel = fkTypeFuel,
        engineVolume = engineVolume,
        note = note,
        fkVibrationSource = fkTypeSource
    )
}