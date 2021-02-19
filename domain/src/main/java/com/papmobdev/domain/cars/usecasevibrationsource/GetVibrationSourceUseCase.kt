package com.papmobdev.domain.cars.usecasevibrationsource

import com.papmobdev.domain.FlowUseCaseOut
import com.papmobdev.domain.cars.models.VibrationSource
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface GetVibrationSourceUseCase : FlowUseCaseOut<List<VibrationSource>>