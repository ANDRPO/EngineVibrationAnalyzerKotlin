package com.papmobdev.domain.cars.usecasecarmarks

import com.papmobdev.domain.FlowUseCaseOut
import com.papmobdev.domain.cars.models.CarMark
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface GetMarksUseCase : FlowUseCaseOut<List<CarMark>>