package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.papmobdev.domain.cars.models.CarGeneration
import com.papmobdev.domain.cars.models.CarMark
import com.papmobdev.domain.cars.models.CarModel
import com.papmobdev.domain.cars.usecasecargeneration.GetGenerationsUseCase
import com.papmobdev.domain.cars.usecasecarmodels.GetModelsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SelectCarViewModel(
    private val getModelsUseCase: GetModelsUseCase,
    private val getGenerationsUseCase: GetGenerationsUseCase
) : ViewModel() {

    private val _liveDataMark: MutableLiveData<CarMark> = MutableLiveData()
    val liveDataMark: LiveData<CarMark> = _liveDataMark

    private val _liveDataModel: MutableLiveData<CarModel> = MutableLiveData()
    val liveDataModel: MutableLiveData<CarModel> = _liveDataModel

    private val _liveDataGeneration: MutableLiveData<CarGeneration> = MutableLiveData()
    val liveDataGeneration: MutableLiveData<CarGeneration> = _liveDataGeneration


    var nextModelIsNotNull: Boolean = true

    var nextGenerationIsNotNull: Boolean = true

    var selectOptionFuel: String? = null


    fun <T> postDataOption(baseCarOption: T, nextOptionIsNull: Boolean) {
        when (baseCarOption) {
            is CarMark -> {
                nextModelIsNotNull = nextOptionIsNull
                _liveDataMark.value = baseCarOption
                _liveDataModel.value = null
                _liveDataGeneration.value = null
            }
            is CarModel -> {
                viewModelScope.launch(Dispatchers.Unconfined)

                nextGenerationIsNotNull = nextOptionIsNull
                _liveDataModel.value = baseCarOption
                _liveDataGeneration.value = null
            }
            is CarGeneration -> {
                _liveDataGeneration.value = baseCarOption
            }
        }
    }

    private suspend fun <T> checkNextOptionsListIsNotNull(carOption: T) {
        when (carOption) {
            is CarMark -> getModelsUseCase(carOption.id).collect {
                nextModelIsNotNull = it.getOrDefault(listOf()).isNotEmpty()
            }
            is CarModel -> getGenerationsUseCase(carOption.id).collect {
                nextGenerationIsNotNull = it.getOrDefault(listOf()).isNotEmpty()
            }
        }
    }
}