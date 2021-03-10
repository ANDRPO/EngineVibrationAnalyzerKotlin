package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.manual

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.ObserveConfigurationCarUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ManualViewModel(
    private val observeConfigurationCarUseCase: ObserveConfigurationCarUseCase
) : ViewModel() {
    fun configuration() = observeConfigurationCarUseCase().asLiveData()
}