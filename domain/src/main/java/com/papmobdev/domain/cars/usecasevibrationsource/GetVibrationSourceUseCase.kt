package com.papmobdev.domain.cars.usecasevibrationsource

import com.papmobdev.domain.FlowUseCaseOut
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface GetVibrationSourceUseCase : FlowUseCaseOut<List<VibrationSource>>