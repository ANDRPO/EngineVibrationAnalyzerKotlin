package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.selectconfigurationcar

import androidx.lifecycle.*
import com.papmobdev.domain.cars.CodeParametersCar
import com.papmobdev.domain.cars.models.*
import com.papmobdev.domain.cars.usecasecargeneration.GetGenerationsUseCase
import com.papmobdev.domain.cars.usecasecarmodels.GetModelsUseCase
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.ObserveConfigurationCarUseCase
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.UpdateConfigurationCarUseCase
import com.papmobdev.domain.cars.usecasetypesfuels.GetTypesFuelUseCase
import com.papmobdev.domain.cars.usecasevibrationsource.GetVibrationSourceUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class SelectCarViewModel(
    private val getModelsUseCase: GetModelsUseCase,
    private val getGenerationsUseCase: GetGenerationsUseCase,
    observeConfigurationCarUseCase: ObserveConfigurationCarUseCase,
    getTypesFuelUseCase: GetTypesFuelUseCase,
    getVibrationSourceUseCase: GetVibrationSourceUseCase,
    private val updateConfigurationCarUseCase: UpdateConfigurationCarUseCase,
) : ViewModel() {

    val liveDataConfiguration: LiveData<Result<CarConfiguration>> =
        observeConfigurationCarUseCase().asLiveData()

    val liveDataTypesFuelList: LiveData<Result<List<TypesFuel>>> =
        getTypesFuelUseCase().asLiveData()

    val liveDataTypeSourceList: LiveData<Result<List<TypesSource>>> =
        getVibrationSourceUseCase().asLiveData()

    private val _showErrorMessage = MutableLiveData<String>()
    val showErrorMessage = _showErrorMessage

    var nextModelIsNotNull: Boolean = true

    var nextGenerationIsNotNull: Boolean = true

    fun checkNextOptionsListIsNotNull(carOption: CodeParametersCar) {
        viewModelScope.launch(Dispatchers.IO) {
            when (carOption) {
                CodeParametersCar.MARK -> liveDataConfiguration.value.let { lastConfiguration ->
                    lastConfiguration?.getOrNull()?.fkCarMark?.let { fkMark ->
                        getModelsUseCase(fkMark).collect {
                            nextModelIsNotNull = it.getOrDefault(listOf()).isNotEmpty()
                        }
                    }
                }
                CodeParametersCar.MODEL -> liveDataConfiguration.value.let { lastConfiguration ->
                    lastConfiguration?.getOrNull()?.fkCarModel?.let { fkModel ->
                        getGenerationsUseCase(fkModel).collect {
                            nextGenerationIsNotNull = it.getOrDefault(listOf()).isNotEmpty()
                        }
                    }
                }
                CodeParametersCar.GENERATION -> return@launch
            }
        }
    }

    fun updateEngineVolumeConfiguration(value: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            liveDataConfiguration.value?.getOrNull()?.let {
                it.engineVolume =
                    if (!value.isNullOrEmpty()) value.toDouble() else null
                updateConfigurationCarUseCase(it).collect()
            }
        }
    }

    fun updateVibrationSourceConfiguration(typeSource: TypesSource) {
        viewModelScope.launch(Dispatchers.IO) {
            liveDataConfiguration.value?.getOrNull()?.let {
                it.typeSource = typeSource
                updateConfigurationCarUseCase(it).collect()
            }
        }
    }

    fun updateTypeFuelConfiguration(typeFuel: TypesFuel) {
        viewModelScope.launch(Dispatchers.IO) {
            liveDataConfiguration.value?.getOrNull()?.let {
                it.typeFuel = typeFuel
                updateConfigurationCarUseCase(it).collect()
            }
        }
    }

    fun updateNoteConfiguration(value: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            liveDataConfiguration.value?.getOrNull()?.let {
                it.note = value
                updateConfigurationCarUseCase(it).collect()
            }
        }
    }
}