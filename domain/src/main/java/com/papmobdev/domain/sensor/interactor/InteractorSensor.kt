package com.papmobdev.domain.sensor.interactor

import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.flow.Flow

interface InteractorSensor {
    fun streamEvent(): Flow<EventModel>
    fun startSensor()
    fun stopSensor()
}