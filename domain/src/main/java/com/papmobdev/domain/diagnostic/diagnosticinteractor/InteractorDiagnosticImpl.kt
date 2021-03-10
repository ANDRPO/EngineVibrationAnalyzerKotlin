package com.papmobdev.domain.diagnostic.diagnosticinteractor

import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.models.CarConfiguration
import com.papmobdev.domain.diagnostic.DiagnosticDataSource
import com.papmobdev.domain.diagnostic.models.DiagnosticModel
import com.papmobdev.domain.sensor.SensorDataSource
import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import java.util.*

@ExperimentalCoroutinesApi
class InteractorDiagnosticImpl(
    private val sensorDataSource: SensorDataSource,
    private val diagnosticDataSource: DiagnosticDataSource,
    private val carsDataSource: CarsDataSource
) : InteractorDiagnostic {

    private val listEvents: MutableList<EventModel> = mutableListOf()

    private val _progress = MutableSharedFlow<Int>()
    override val progress: SharedFlow<Int> = _progress

    private val _stateDiagnostic = MutableSharedFlow<StatesDiagnostic>()
    override val stateDiagnostic: SharedFlow<StatesDiagnostic> = _stateDiagnostic

    override suspend fun startDiagnostic() = try {
        setState(StatesDiagnostic.START)
        startSensor()

        diagnostics()

        stopSensor()

        sendDiagnosticData(listEvents.toList())

        listEvents.clear()
        setState(StatesDiagnostic.SUCCESS)
    } catch (e: CancellationException) {
        stopSensor()
        listEvents.clear()
        setState(StatesDiagnostic.CANCEL)
    } catch (e: Exception) {
        stopSensor()
        listEvents.clear()
        setState(StatesDiagnostic.ERROR)
    }

    override suspend fun cancelDiagnostic() {
        try {
            stopSensor()
            listEvents.clear()
            setState(StatesDiagnostic.CANCEL)
        } catch (e: Exception) {
            listEvents.clear()
            setState(StatesDiagnostic.ERROR)
        }
    }

    private suspend fun setState(statesDiagnostic: StatesDiagnostic) {
        _stateDiagnostic.emit(statesDiagnostic)
    }

    private fun startSensor() = sensorDataSource.startSensor()
    private fun getStreamEvent() = sensorDataSource.getStreamEvents()
    private fun stopSensor() = sensorDataSource.stopSensor()

    private suspend fun diagnostics() {

        var readEventsIsActive = false
        val time = System.currentTimeMillis()

        while (System.currentTimeMillis() - time < 10000) {

            _progress.emit((System.currentTimeMillis() - time).toInt() / 10)

            if (System.currentTimeMillis() - time > 3000 && !readEventsIsActive) {
                readEventsIsActive = true
                CoroutineScope(currentCoroutineContext()).launch {
                    readEvents()
                }
            }
        }
    }

    private suspend fun readEvents() = getStreamEvent().collect {
        listEvents.add(it)
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
