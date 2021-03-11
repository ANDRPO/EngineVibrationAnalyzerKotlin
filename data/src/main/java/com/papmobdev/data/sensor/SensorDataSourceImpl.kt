package com.papmobdev.data.sensor

import com.papmobdev.domain.sensor.SensorDataSource
import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class SensorDataSourceImpl(
    private val appAccelerometer: AppAccelerometer
) : SensorDataSource {

    override fun startSensor() = appAccelerometer.start()

    override fun getStreamEvents(): Flow<EventModel> =
        appAccelerometer.streamEvents().flowOn(Dispatchers.IO)

    override fun stopSensor() {
        appAccelerometer.stop()
    }
}