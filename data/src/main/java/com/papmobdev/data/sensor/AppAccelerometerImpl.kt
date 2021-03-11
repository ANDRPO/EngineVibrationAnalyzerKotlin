package com.papmobdev.data.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.papmobdev.data.mapping.toDomain
import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class AppAccelerometerImpl(context: Context) : AppAccelerometer {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer =
        sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) as Sensor

    private val _events = MutableSharedFlow<EventModel>(
        replay = 0,
        extraBufferCapacity = 2_000,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    private val events: SharedFlow<EventModel> = _events

    private val listener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let { _events.tryEmit(it.toDomain()) }
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

    override fun streamEvents(): Flow<EventModel> = events

    override fun stop() {
        sensorManager.unregisterListener(listener, accelerometer)
    }
}