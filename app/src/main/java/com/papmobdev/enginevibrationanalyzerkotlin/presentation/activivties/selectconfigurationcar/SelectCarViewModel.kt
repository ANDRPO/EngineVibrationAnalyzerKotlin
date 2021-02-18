package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.selectconfigurationcar

import androidx.lifecycle.*
import com.papmobdev.domain.cars.CodeOptionsCar
import com.papmobdev.domain.cars.models.CarConfigurationModel
import com.papmobdev.domain.cars.models.TypeFuel
import com.papmobdev.domain.cars.usecasecargeneration.GetGenerationsUseCase
import com.papmobdev.domain.cars.usecasecarmodels.GetModelsUseCase
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.ObserveConfigurationCarUseCase
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.UpdateConfigurationCarUseCase
import com.papmobdev.domain.cars.usecasetypesfuels.GetTypesFuelUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class SelectCarViewModel(
    private val getModelsUseCase: GetModelsUseCase,
    private val getGenerationsUseCase: GetGenerationsUseCase,
    observeConfigurationCarUseCase: ObserveConfigurationCarUseCase,
    getTypesFuelUseCase: GetTypesFuelUseCase,
    private val updateConfigurationCarUseCase: UpdateConfigurationCarUseCase,
) : ViewModel() {

    val liveDataConfiguration: LiveData<Result<CarConfigurationModel>> =
        observeConfigurationCarUseCase.execute().asLiveData()

    val liveDataTypesFuelList: LiveData<Result<List<TypeFuel>>> = getTypesFuelUseCase().asLiveData()

    private val _showErrorMessage = MutableLiveData<String>()
    val showErrorMessage = _showErrorMessage

    var nextModelIsNotNull: Boolean = true

    var nextGenerationIsNotNull: Boolean = true

    fun checkNextOptionsListIsNotNull(carOption: CodeOptionsCar) {
        viewModelScope.launch(Dispatchers.IO) {
            when (carOption) {
                CodeOptionsCar.MARK -> liveDataConfiguration.value.let { lastConfiguration ->
                    lastConfiguration?.getOrNull()?.fkCarMark?.let { fkMark ->
                        getModelsUseCase(fkMark).collect {
                            nextModelIsNotNull = it.getOrDefault(listOf()).isNotEmpty()
                        }
                    }
                }
                CodeOptionsCar.MODEL -> liveDataConfiguration.value.let { lastConfiguration ->
                    lastConfiguration?.getOrNull()?.fkCarModel?.let { fkModel ->
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
            val configuration = liveDataConfiguration.value?.getOrNull()
            if (configuration != null) {
                configuration.engineVolume =
                    if (!value.isNullOrEmpty()) value.toDouble() else null
                updateConfigurationCarUseCase.execute(configuration).collect()
            }
        }
    }

    fun updateTypeFuelConfiguration(fkFuel: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val configuration = liveDataConfiguration.value?.getOrNull()
            if (configuration != null) {
                configuration.fkTypeFuel = fkFuel
                updateConfigurationCarUseCase.execute(configuration).collect()
            }
        }
    }

    fun updateNoteConfiguration(value: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val configuration = liveDataConfiguration.value?.getOrNull()
            if (configuration != null) {
                configuration.note = value
                updateConfigurationCarUseCase.execute(configuration).collect()
            }
        }
    }
}