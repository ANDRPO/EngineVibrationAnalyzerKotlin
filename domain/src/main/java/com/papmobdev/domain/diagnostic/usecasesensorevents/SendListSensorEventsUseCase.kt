package com.papmobdev.domain.diagnostic.usecasesensorevents

import com.papmobdev.domain.FlowUseCase
import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface SendListSensorEventsUseCase : FlowUseCase<SendEventModel, Boolean>