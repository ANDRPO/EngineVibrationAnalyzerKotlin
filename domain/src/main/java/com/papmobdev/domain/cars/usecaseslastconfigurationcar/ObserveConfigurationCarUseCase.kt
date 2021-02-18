package com.papmobdev.domain.cars.usecaseslastconfigurationcar

import com.papmobdev.domain.FlowUseCaseOut
import com.papmobdev.domain.cars.models.CarConfigurationModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface ObserveConfigurationCarUseCase : FlowUseCaseOut<CarConfigurationModel>