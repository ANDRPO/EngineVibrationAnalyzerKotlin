package com.papmobdev.domain.diagnostic.diagnosticinteractor

import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.models.CarConfiguration
import com.papmobdev.domain.diagnostic.DiagnosticDataSource
import com.papmobdev.domain.diagnostic.models.DiagnosticModel
import com.papmobdev.domain.sensor.SensorDataSource
import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*

class InteractorDiagnosticImpl(
    private val sensorDataSource: SensorDataSource,
    private val diagnosticDataSource: DiagnosticDataSource,
    private val carsDataSource: CarsDataSource
) : InteractorDiagnostic {

    private var diagnostic: Job = Job()

    private val listEvents: MutableList<EventModel> = mutableListOf()

    private val _progress = MutableSharedFlow<Int>()
    override val progress = _progress.asSharedFlow()

    private val _stateDiagnostic = MutableStateFlow(StatesDiagnostic.NONE)
    override val stateDiagnostic = _stateDiagnostic.asStateFlow()

    override fun startDiagnostic(scope: CoroutineScope) {
        diagnostic = scope.launch(Dispatchers.IO) {
            diagnostics(scope)
        }
    }

    override fun cancelDiagnostic() {
        try {
            diagnostic.cancel()
            stopSensor()
            listEvents.clear()
            setState(StatesDiagnostic.CANCEL)
        } catch (e: Exception) {
            setState(StatesDiagnostic.ERROR)
        }
    }

    private fun setState(statesDiagnostic: StatesDiagnostic) {
        _stateDiagnostic.value = statesDiagnostic
    }

    private fun startSensor() = sensorDataSource.startSensor()
    private fun getStreamEvent() = sensorDataSource.getStreamEvents()
    private fun stopSensor() = sensorDataSource.stopSensor()


    private suspend fun diagnostics(scope: CoroutineScope) =
        try {
            setState(StatesDiagnostic.START)

            startSensor()

            var count = 0
            while (count < 1000) {
                delay(10)
                count++
                _progress.emit(count)
                if (count == 300) scope.launch {
                    readEvents()
                }
            }

            stopSensor()
            sendDiagnosticData(listEvents)
            listEvents.clear()
            setState(StatesDiagnostic.SUCCESS)
        } catch (e: Throwable) {
            setState(StatesDiagnostic.CANCEL)
        }

    private suspend fun readEvents() {
        getStreamEvent().collect {
            listEvents.add(it)
        }
    }

    private suspend fun sendDiagnosticData(list: List<EventModel>) =
        diagnosticDataSource.sendDiagnosticAndEventsData(
            getCarConfiguration().toDiagnosticModel(),
            list
        )

    private suspend fun getCarConfiguration() =
        carsDataSource.getLastCarConfiguration().firstOrNull() ?: CarConfiguration()

    private fun CarConfiguration.toDiagnosticModel() = DiagnosticModel(
        idDiagnostic = null,
        dateTime = Date(),
        fkCarMark = fkCarMark,
        fkCarModel = fkCarModel,
        fkCarGeneration = fkCarGeneration,
        fkTypeFuel = fkTypeFuel,
        engineVolume = engineVolume,
        fkVibrationSource = fkTypeSource,
        note = note
    )
}
