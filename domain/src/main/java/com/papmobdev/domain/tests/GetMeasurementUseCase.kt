package com.papmobdev.domain.tests

import com.papmobdev.domain.FlowUseCase
import com.papmobdev.domain.tests.models.MeasurementModel
import com.papmobdev.domain.tests.models.TestModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface GetMeasurementUseCase : FlowUseCase<TestModel, List<MeasurementModel>>