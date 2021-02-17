package com.papmobdev.domain.sensor

import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.flow.Flow

interface SensorDataSource {
    fun getStreamEvents(): Flow<EventModel>
}