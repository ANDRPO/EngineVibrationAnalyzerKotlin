package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.diagnostic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.ObserveConfigurationCarUseCase
import com.papmobdev.domain.sensor.usecaseobservesensor.ObserveSensorUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ManualViewModel(
    private val observeConfigurationCarUseCase: ObserveConfigurationCarUseCase
) : ViewModel() {
    var checkNotShowActivity: Boolean = false

    fun configuration() = observeConfigurationCarUseCase().asLiveData()

}