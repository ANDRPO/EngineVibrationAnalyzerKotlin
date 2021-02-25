package com.papmobdev.domain.sensor.interactor

import com.papmobdev.domain.sensor.SensorDataSource
import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

@ExperimentalCoroutinesApi
class InteractorSensorImpl(
    private val sensorDataSource: SensorDataSource
) : InteractorSensor {
    override fun streamEvent(): Flow<EventModel> = sensorDataSource.getStreamEvents().flowOn(Dispatchers.Default)

    override fun startSensor() = sensorDataSource.startSensor()

    override fun stopSensor() = sensorDataSource.stopSensor()
}