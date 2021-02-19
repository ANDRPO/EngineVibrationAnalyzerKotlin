package com.papmobdev.domain.sensor.interactorsensor

import com.papmobdev.domain.sensor.SensorDataSource
import com.papmobdev.domain.sensor.models.EventModel
import com.papmobdev.domain.sensor.usecaseobservesensor.ObserveSensorUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class InteractorSensorImpl(
    private val observeSensorUseCase: ObserveSensorUseCase,
    private val sensorDataSource: SensorDataSource
) : InteractorSensor {
    override fun streamEvent(): Flow<EventModel> = observeSensorUseCase.execute()

    override fun startSensor() = sensorDataSource.startSensor()

    override fun stopSensor() = sensorDataSource.stopSensor()
}