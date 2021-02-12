package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import androidx.lifecycle.*
import com.papmobdev.domain.cars.CodeOptionsCar
import com.papmobdev.domain.cars.models.LastCarConfigurationModel
import com.papmobdev.domain.cars.models.TypeFuel
import com.papmobdev.domain.cars.usecasecargeneration.GetGenerationsUseCase
import com.papmobdev.domain.cars.usecasecarmodels.GetModelsUseCase
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.GetConfigurationCarUseCase
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.UpdateConfigurationCarUseCase
import com.papmobdev.domain.cars.usecasetypesfuels.GetTypesFuelUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SelectCarViewModel(
    private val getModelsUseCase: GetModelsUseCase,
    private val getGenerationsUseCase: GetGenerationsUseCase,
    private val getConfigurationCarUseCase: GetConfigurationCarUseCase,
    getTypesFuelUseCase: GetTypesFuelUseCase,
    private val updateConfigurationCarUseCase: UpdateConfigurationCarUseCase
) : ViewModel() {

    private val _liveDataConfiguration: MutableLiveData<LastCarConfigurationModel> =
        MutableLiveData()
    val liveDataConfiguration: LiveData<LastCarConfigurationModel> = _liveDataConfiguration

    val liveDataTypesFuelList: LiveData<Result<List<TypeFuel>>> = getTypesFuelUseCase().asLiveData()

    var nextModelIsNotNull: Boolean = true

    var nextGenerationIsNotNull: Boolean = true

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            getConfigurationCarUseCase.execute().collect { result ->
                result.onSuccess {
                    _liveDataConfiguration.postValue(it)
                }
                result.onFailure {
                    throw Exception("Error getting the last item configuration")
                }
            }
        }
    }

    fun checkNextOptionsListIsNotNull(carOption: CodeOptionsCar) {
        viewModelScope.launch(Dispatchers.IO) {
            when (carOption) {
                CodeOptionsCar.MARK -> _liveDataConfiguration.value?.let { lastConfiguration ->
                    lastConfiguration.fkCarMark?.let { fkMark ->
                        getModelsUseCase(fkMark).collect {
                            nextModelIsNotNull = it.getOrDefault(listOf()).isNotEmpty()
                        }
                    }
                }
                CodeOptionsCar.MODEL -> _liveDataConfiguration.value?.let { lastConfiguration ->
                    lastConfiguration.fkCarModel?.let { fkModel ->
                        getGenerationsUseCase(fkModel).collect {
                            nextGenerationIsNotNull = it.getOrDefault(listOf()).isNotEmpty()
                        }
                    }
                }
                CodeOptionsCar.GENERATION -> return@launch
            }
        }
    }

    fun updateEngineVolumeConfiguration(value: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val configuration = _liveDataConfiguration.value
            if (configuration != null) {
                configuration.engineVolume = if(!value.isNullOrEmpty()) value.toDouble() else null
                updateConfigurationCarUseCase.execute(configuration).collect()
            }
        }
    }

    fun updateTypeFuelConfiguration(fkFuel: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val configuration = _liveDataConfiguration.value
            if (configuration != null) {
                configuration.fkTypeFuel = fkFuel
                updateConfigurationCarUseCase.execute(configuration).collect()
            }
        }
    }

    fun updateNoteConfiguration(value: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val configuration = _liveDataConfiguration.value
            if (configuration != null) {
                configuration.note = value
                updateConfigurationCarUseCase.execute(configuration).collect()
            }
        }
    }
}