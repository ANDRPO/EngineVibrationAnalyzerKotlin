package com.papmobdev.domain.cars.usecasetypesfuels

import com.papmobdev.domain.FlowUseCaseOut
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface GetTypesFuelUseCase : FlowUseCaseOut<List<TypeFuel>>