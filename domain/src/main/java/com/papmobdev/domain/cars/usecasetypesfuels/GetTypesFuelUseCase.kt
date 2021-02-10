package com.papmobdev.domain.cars.usecasetypesfuels

import com.papmobdev.domain.FlowUseCaseOut
import com.papmobdev.domain.cars.models.TypeFuel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface GetTypesFuelUseCase : FlowUseCaseOut<List<TypeFuel>>