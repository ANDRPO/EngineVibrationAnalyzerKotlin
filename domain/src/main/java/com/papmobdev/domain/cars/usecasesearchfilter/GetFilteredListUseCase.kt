package com.papmobdev.domain.cars.usecasesearchfilter

import com.papmobdev.domain.BiFlowUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface GetFilteredListUseCase: BiFlowUseCase<String, MutableList<*>, MutableList<*>>