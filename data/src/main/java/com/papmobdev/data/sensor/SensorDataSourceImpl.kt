package com.papmobdev.data.sensor

import com.papmobdev.data.mapping.toDomain
import com.papmobdev.domain.sensor.SensorDataSource
import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class SensorDataSourceImpl(
    private val appAccelerometer: AppAccelerometer
) : SensorDataSource {
    override fun startSensor() = appAccelerometer.start()

    override fun getStreamEvents(): Flow<EventModel> = appAccelerometer.streamEvents().map {
        it.toDomain()
    }.flowOn(Dispatchers.IO)

    override fun stopSensor() = appAccelerometer.stop()
}