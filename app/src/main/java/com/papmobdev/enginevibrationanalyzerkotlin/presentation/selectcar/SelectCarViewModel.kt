package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import android.database.DataSetObservable
import android.database.DataSetObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.papmobdev.domain.cars.models.BaseCarOption
import com.papmobdev.domain.cars.models.CarGeneration
import com.papmobdev.domain.cars.models.CarMark
import com.papmobdev.domain.cars.models.CarModel
import com.papmobdev.domain.cars.usecasecargeneration.GetGenerationsUseCase
import com.papmobdev.domain.cars.usecasecarmodels.GetModelsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.Duration
import kotlin.coroutines.coroutineContext

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

    var selectOptionFuel: String? = null

    fun <T : BaseCarOption> postDataOption(baseCarOption: T?, nextOptionIsNull: Boolean) {
        when (baseCarOption) {
            is CarMark -> {
                checkNextOptionsListIsNotNull(baseCarOption)
                nextModelIsNotNull = nextOptionIsNull
                liveDataMark.value = baseCarOption
                liveDataModel.value = null
                liveDataGeneration.value = null
            }
            is CarModel -> {
                checkNextOptionsListIsNotNull(baseCarOption)
                nextGenerationIsNotNull = nextOptionIsNull
                liveDataModel.value = baseCarOption
                liveDataGeneration.value = null
            }
            is CarGeneration -> {
                liveDataGeneration.value = baseCarOption
            }
        }
    }


    private fun <T : BaseCarOption> checkNextOptionsListIsNotNull(carOption: T) {
        when (carOption) {
            is CarMark -> getModelsUseCase(carOption.id).asLiveData().observeForever {
                nextModelIsNotNull = it.getOrDefault(listOf()).isNotEmpty()
            }
            is CarModel -> getGenerationsUseCase(carOption.id).asLiveData().observeForever {
                nextGenerationIsNotNull = it.getOrDefault(listOf()).isNotEmpty()
            }
        }
    }
}