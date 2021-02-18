package com.papmobdev.data.sensor

import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import kotlinx.coroutines.flow.Flow

interface AppAccelerometer {
    fun start()
    fun streamEvents(): Flow<SensorEvent>
    fun stop()
}