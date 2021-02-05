package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

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

    private fun getLastConfiguration(): LiveData<Result<LastCarConfigurationModel>> =
        getConfigurationCarUseCase().asLiveData()

    var nextModelIsNotNull: Boolean = true

    var nextGenerationIsNotNull: Boolean = true

    var selectOptionFuel: String? = null

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            getConfigurationCarUseCase.execute().collect { result ->
                result.onSuccess {
                    _liveDataConfiguration.value = it
                }
                result.onFailure {
                    TODO("Add Exception")
                }
            }
        }
    }

    private suspend fun checkNextOptionsListIsNotNull(carOption: CodeOptionsCar) {
        when (carOption) {
            CodeOptionsCar.MARK -> liveDataConfiguration.value?.let { lastConfiguration ->
                getModelsUseCase(lastConfiguration.fkCarMark).collect {
                    nextModelIsNotNull = it.getOrDefault(listOf()).isNotEmpty()
                }
            }
            CodeOptionsCar.MODEL -> liveDataConfiguration.value?.let { lastConfiguration ->
                getGenerationsUseCase(lastConfiguration.fkCarModel).collect {
                    nextGenerationIsNotNull = it.getOrDefault(listOf()).isNotEmpty()
                }
            }
        }
    }

    fun notifySelection(carOption: CodeOptionsCar) {
        viewModelScope.launch(Dispatchers.IO) {
            checkNextOptionsListIsNotNull(carOption)
        }
    }
}