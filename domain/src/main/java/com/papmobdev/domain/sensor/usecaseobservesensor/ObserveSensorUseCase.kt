package com.papmobdev.domain.sensor.usecaseobservesensor

import com.papmobdev.domain.CallbackFlow
import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface ObserveSensorUseCase : CallbackFlow<EventModel>