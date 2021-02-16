package com.papmobdev.data.sensor

import android.hardware.SensorEvent
import kotlinx.coroutines.flow.Flow

interface AppAccelerometer {
    fun start()
    fun stop()
    suspend fun streamEvents(): Flow<SensorEvent>
}