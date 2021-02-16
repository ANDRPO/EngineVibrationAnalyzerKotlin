package com.papmobdev.domain.diagnostics.usecaseobservesensor

import com.papmobdev.domain.FlowUseCaseOut
import com.papmobdev.domain.diagnostics.models.EventModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface ObserveSensorUseCase : FlowUseCaseOut<EventModel> {
}