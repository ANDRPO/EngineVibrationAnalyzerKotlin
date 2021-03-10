package com.papmobdev.data.sensor

import android.content.Context
import android.hardware.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.*

class AppAccelerometerImpl(context: Context) : AppAccelerometer {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer =
        sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) as Sensor

    private val _events = MutableSharedFlow<SensorEvent>(
        replay = 0,
        extraBufferCapacity = 2_000,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    private val events: SharedFlow<SensorEvent> = _events.asSharedFlow()

    private val listener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let { _events.tryEmit(it) }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }
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

    override fun stop() {
        sensorManager.unregisterListener(listener, accelerometer)
    }
}