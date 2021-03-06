package com.papmobdev.domain.cars.usecasecarmodels

import com.papmobdev.domain.FlowUseCase
import com.papmobdev.domain.cars.models.CarModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface GetModelsUseCase : FlowUseCase<Int, List<CarModel>>