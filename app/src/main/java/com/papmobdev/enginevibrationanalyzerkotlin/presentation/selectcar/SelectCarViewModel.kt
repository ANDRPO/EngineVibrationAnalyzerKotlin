package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import android.util.Log
import androidx.lifecycle.*
import com.papmobdev.domain.cars.CodeOptionsCar
import com.papmobdev.domain.cars.models.LastCarConfigurationModel
import com.papmobdev.domain.cars.usecasecargeneration.GetGenerationsUseCase
import com.papmobdev.domain.cars.usecasecarmodels.GetModelsUseCase
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.GetConfigurationCarUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SelectCarViewModel(
    private val getModelsUseCase: GetModelsUseCase,
    private val getGenerationsUseCase: GetGenerationsUseCase,
    private val getConfigurationCarUseCase: GetConfigurationCarUseCase
) : ViewModel() {

    private val _liveDataConfiguration: MutableLiveData<LastCarConfigurationModel> =
        MutableLiveData()

    val liveDataConfiguration: LiveData<LastCarConfigurationModel> = _liveDataConfiguration

    var nextModelIsNotNull: Boolean = true

    var nextGenerationIsNotNull: Boolean = true

    var selectOptionFuel: String? = null

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            getConfigurationCarUseCase.execute().collect { result ->
                result.onSuccess {
                    _liveDataConfiguration.postValue(it)
                }
                result.onFailure {
                    TODO("Add Exception")
                }
            }
        }.start()
    }

    fun checkNextOptionsListIsNotNull(carOption: CodeOptionsCar) {
        viewModelScope.launch(Dispatchers.IO) {
            when (carOption) {
                CodeOptionsCar.MARK -> _liveDataConfiguration.value?.let { lastConfiguration ->
                    lastConfiguration.fkCarMark?.let { fkMark ->
                        getModelsUseCase(fkMark).collect {
                            nextModelIsNotNull = it.getOrDefault(listOf()).isNotEmpty()
                            Log.d("NEXTMODEL", nextModelIsNotNull.toString())
                        }
                    }
                }
                CodeOptionsCar.MODEL -> _liveDataConfiguration.value?.let { lastConfiguration ->
                    lastConfiguration.fkCarModel?.let { fkModel ->
                        getGenerationsUseCase(fkModel).collect {
                            nextGenerationIsNotNull = it.getOrDefault(listOf()).isNotEmpty()
                            Log.d("NEXTGENERATION", nextGenerationIsNotNull.toString())
                        }
                    }
                }
                /*CodeOptionsCar.GENERATION -> throw Exception("Generation table has no children")*/
            }
        }
    }

    fun notifySelection(carOption: CodeOptionsCar) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchData()
        }
    }
}