package com.papmobdev.domain.sensor

import com.papmobdev.domain.cars.models.CarConfigurationModel
import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.flow.Flow

interface SensorDataSource {
    fun startSensor()
    fun getStreamEvents(): Flow<EventModel>
    fun stopSensor()
}