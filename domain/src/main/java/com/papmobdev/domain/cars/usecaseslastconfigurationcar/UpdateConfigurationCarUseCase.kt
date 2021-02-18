package com.papmobdev.domain.cars.usecaseslastconfigurationcar

import com.papmobdev.domain.FlowUseCase
import com.papmobdev.domain.cars.models.CarConfigurationModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface UpdateConfigurationCarUseCase : FlowUseCase<CarConfigurationModel, Boolean>