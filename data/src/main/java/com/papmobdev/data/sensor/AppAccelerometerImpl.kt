package com.papmobdev.data.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AppAccelerometerImpl(context: Context) : AppAccelerometer {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer =
        sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) as Sensor

    private val _events = MutableSharedFlow<SensorEvent>(
        replay = 100,
        extraBufferCapacity = 1000,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    private val events = _events.asSharedFlow()

    private val listener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null) {
                _events.tryEmit(event)
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    override fun start() {
        sensorManager.registerListener(
            listener,
            accelerometer,
            SensorManager.SENSOR_DELAY_FASTEST
        )
    }


    @ExperimentalCoroutinesApi
    override fun streamEvents(): Flow<SensorEvent> = events

    override fun stop() = sensorManager.unregisterListener(listener, accelerometer)

}