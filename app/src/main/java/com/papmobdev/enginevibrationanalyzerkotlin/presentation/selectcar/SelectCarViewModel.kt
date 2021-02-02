package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.papmobdev.data.entities.CarGenerationEntity
import com.papmobdev.data.entities.CarMarkEntity
import com.papmobdev.data.entities.CarModelEntity
import com.papmobdev.domain.cars.models.BaseCarOption
import com.papmobdev.domain.cars.models.CarGeneration
import com.papmobdev.domain.cars.models.CarMark
import com.papmobdev.domain.cars.models.CarModel
import com.papmobdev.domain.cars.usecasecargeneration.GetGenerationsUseCase
import com.papmobdev.domain.cars.usecasecarmarks.GetMarksUseCase
import com.papmobdev.domain.cars.usecasecarmodels.GetModelsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SelectCarViewModel(
    private val getModelsUseCase: GetModelsUseCase,
    private val getGenerationsUseCase: GetGenerationsUseCase
) : ViewModel() {

    var liveDataMark: MutableLiveData<CarMark?> = MutableLiveData()
    var liveDataModel: MutableLiveData<CarModel?> = MutableLiveData()
    var liveDataGeneration: MutableLiveData<CarGeneration?> = MutableLiveData()

    var nextModelIsNotNull: Boolean = true
    var nextGenerationIsNotNull: Boolean = true

    fun <T : BaseCarOption> postDataOption(baseCarOption: T?, nextOptionIsNull: Boolean) {
        when (baseCarOption) {
            is CarMark -> {
                GlobalScope.launch(Dispatchers.IO) {
                    checkNextOptionIsNotNull(baseCarOption)
                }.start()
                nextModelIsNotNull = nextOptionIsNull
                liveDataMark.value = baseCarOption
                liveDataModel.value = null
                liveDataGeneration.value = null
            }
            is CarModel -> {
                GlobalScope.launch(Dispatchers.IO) {
                    checkNextOptionIsNotNull(baseCarOption)
                }.start()
                nextGenerationIsNotNull = nextOptionIsNull
                liveDataModel.value = baseCarOption
                liveDataGeneration.value = null
            }
            is CarGeneration -> {
                liveDataGeneration.value = baseCarOption
            }

        }
    }

    private suspend fun <T : BaseCarOption> checkNextOptionIsNotNull(carOption: T) {
        when (carOption) {
            is CarMark -> getModelsUseCase.execute(carOption.id).collect {
                nextModelIsNotNull = it.getOrDefault(listOf()).isNotEmpty()
            }
            is CarModel -> getGenerationsUseCase(carOption.id).collect {
                nextGenerationIsNotNull = it.getOrDefault(listOf()).isNotEmpty()
            }
        }
    }


}