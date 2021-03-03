package com.papmobdev.domain.diagnostic.diagnosticinteractor

import android.util.Log
import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.models.CarConfiguration
import com.papmobdev.domain.diagnostic.DiagnosticDataSource
import com.papmobdev.domain.diagnostic.models.DiagnosticModel
import com.papmobdev.domain.sensor.SensorDataSource
import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.timerTask

@ExperimentalCoroutinesApi
class InteractorDiagnosticImpl(
    private val sensorDataSource: SensorDataSource,
    private val diagnosticDataSource: DiagnosticDataSource,
    private val carsDataSource: CarsDataSource
) : InteractorDiagnostic {

    private val listEvents: MutableList<EventModel> = mutableListOf()

    private val timer =
        fixedRateTimer(name = "Timer10Seconds", daemon = true, initialDelay = 0L, period = 10L) {
            Log.e("тикТАК", "ТИКтак...тиктаааак")
        }

    private val _progress = MutableSharedFlow<Int>(0)
    override val progress = _progress.asSharedFlow()

    private val _stateDiagnostic = MutableStateFlow(StatesDiagnostic.NONE)
    override val stateDiagnostic = _stateDiagnostic.asStateFlow()

    override suspend fun startDiagnostic() {
        setState(StatesDiagnostic.START)
        diagnostics()
    }

    override fun cancelDiagnostic() {
        try {
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

    private suspend fun diagnostics() =
        try {
            startSensor()

            val time = System.currentTimeMillis()
            var activeStreamEvents = false
            while (System.currentTimeMillis() - time < 10000) {
                _progress.emit((System.currentTimeMillis() - time).toInt()/10)
                if (System.currentTimeMillis() - time > 3000 && !activeStreamEvents) {
                    Log.e("Time>3000", "ACTIVE")
                    CoroutineScope(currentCoroutineContext()).launch {
                        readEvents()
                        Log.e("ACTIVEREADEVENTS", "ACTIVE")
                    }
                    activeStreamEvents = true
                }
            }
            Log.e("TIME", (System.currentTimeMillis() - time).toString())

            stopSensor()
            sendDiagnosticData(listEvents)
            listEvents.clear()
            setState(StatesDiagnostic.SUCCESS)
        } catch (e: Throwable) {
            stopSensor()
            listEvents.clear()
            setState(StatesDiagnostic.CANCEL)
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
