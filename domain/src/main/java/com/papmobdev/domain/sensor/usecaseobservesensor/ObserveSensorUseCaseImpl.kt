package com.papmobdev.domain.sensor.usecaseobservesensor

import com.papmobdev.domain.sensor.SensorDataSource
import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class ObserveSensorUseCaseImpl(
    private val sensorDataSource: SensorDataSource
): ObserveSensorUseCase {
    override fun execute(): Flow<EventModel> = sensorDataSource.getStreamEvents()
}