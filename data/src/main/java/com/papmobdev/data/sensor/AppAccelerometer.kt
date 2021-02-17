package com.papmobdev.data.sensor

import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import kotlinx.coroutines.flow.Flow

interface AppAccelerometer {
    fun streamEvents(): Flow<SensorEvent>
}