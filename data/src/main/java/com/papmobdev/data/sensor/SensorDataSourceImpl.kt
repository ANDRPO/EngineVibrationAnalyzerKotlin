package com.papmobdev.data.sensor

import android.hardware.SensorEvent
import com.papmobdev.domain.sensor.SensorDataSource
import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SensorDataSourceImpl(
    private val appAccelerometer: AppAccelerometer
) : SensorDataSource {
    override fun getStreamEvents(): Flow<EventModel> = appAccelerometer.streamEvents().map {
        it.toDomain()
    }

    private fun SensorEvent.toDomain(): EventModel = EventModel(
        id = null,
        x_value = values[0],
        y_value = values[1],
        z_value = values[2],
        timestamp = timestamp
    )
}