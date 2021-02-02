package com.papmobdev.domain.cars.usecasecargeneration

import com.papmobdev.domain.FlowUseCase
import com.papmobdev.domain.cars.models.CarGeneration
import com.papmobdev.domain.cars.models.CarModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface GetGenerationsUseCase : FlowUseCase<Int, List<CarGeneration>>