package com.papmobdev.domain.cars.usecaseslastconfigurationcar

import com.papmobdev.domain.FlowUseCase
import com.papmobdev.domain.cars.models.CarConfiguration
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface UpdateConfigurationCarUseCase : FlowUseCase<CarConfiguration, Boolean>