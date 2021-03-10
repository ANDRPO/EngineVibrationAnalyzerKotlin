package com.papmobdev.data.sensor

import com.papmobdev.data.mapping.toDomain
import com.papmobdev.domain.sensor.SensorDataSource
import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class SensorDataSourceImpl(
    private val appAccelerometer: AppAccelerometer
) : SensorDataSource {
    override fun startSensor() = appAccelerometer.start()

    override fun getStreamEvents(): Flow<EventModel> = appAccelerometer.streamEvents().map {
        it.toDomain()
    }.flowOn(Dispatchers.IO).distinctUntilChanged { old, new ->
        old.timestamp == new.timestamp
    }

    override fun stopSensor() = appAccelerometer.stop()
}