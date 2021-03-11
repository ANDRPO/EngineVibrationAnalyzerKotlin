package com.papmobdev.data.sensor

import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.flow.Flow

interface AppAccelerometer {
    fun start()
    fun streamEvents(): Flow<EventModel>
    fun stop()
}