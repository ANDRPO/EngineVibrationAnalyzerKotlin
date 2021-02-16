package com.papmobdev.data.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow

class AppAccelerometerImpl(context: Context) : AppAccelerometer {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) as Sensor

    override fun start() {
        sensorManager.registerListener(
            SensorListenerImpl,
            accelerometer,
            SensorManager.SENSOR_DELAY_FASTEST
        )
    }

    override fun stop() {
        sensorManager.unregisterListener(SensorListenerImpl, accelerometer)
    }

    @ExperimentalCoroutinesApi
    override suspend fun streamEvents(): Flow<SensorEvent> = channelFlow {
        val listener = object: SensorEventListener{
            override fun onSensorChanged(event: SensorEvent?) {
                send(event)
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                TODO("Not yet implemented")
            }
        }
    }

}