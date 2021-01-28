package com.papmobdev.enginevibrationanalyzerkotlin.domain.cars.marks

import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.CarMarkEntity
import com.papmobdev.enginevibrationanalyzerkotlin.domain.FlowUseCaseOut
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface GetMarksUseCase : FlowUseCaseOut<List<CarMarkEntity>>