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

    private lateinit var flow: Flow<SensorEvent>
    private lateinit var listener: SensorEventListener

    private fun <T> Channel<T>.asFlow() =
        this.receiveAsFlow().buffer(capacity = Channel.RENDEZVOUS)

    override fun start() {
        flow = flow {
            
        }
        listener = object :SensorEventListener{
            override fun onSensorChanged(event: SensorEvent?) {

            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }
        }

        sensorManager.registerListener(
            listener,
            accelerometer,
            SensorManager.SENSOR_DELAY_FASTEST
        )
    }

    @ExperimentalCoroutinesApi
    override fun streamEvents(): Flow<SensorEvent> = flow

    override fun stop() {
        sensorManager.unregisterListener(listener, accelerometer)
    }
}