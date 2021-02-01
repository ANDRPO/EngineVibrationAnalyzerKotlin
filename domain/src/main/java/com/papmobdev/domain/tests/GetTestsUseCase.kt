package com.papmobdev.domain.tests

import com.papmobdev.domain.FlowUseCaseOut
import com.papmobdev.domain.tests.models.TestModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface GetTestsUseCase : FlowUseCaseOut<List<TestModel>>