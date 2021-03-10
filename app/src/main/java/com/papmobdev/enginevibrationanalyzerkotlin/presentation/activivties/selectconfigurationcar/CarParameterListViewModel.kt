package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.selectconfigurationcar

import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.lifecycle.*
import com.papmobdev.domain.cars.CodeParametersCar
import com.papmobdev.domain.cars.models.CarGeneration
import com.papmobdev.domain.cars.models.CarMark
import com.papmobdev.domain.cars.models.CarModel
import com.papmobdev.domain.cars.models.CarConfiguration
import com.papmobdev.domain.cars.usecasecargeneration.GetGenerationsUseCase
import com.papmobdev.domain.cars.usecasecarmarks.GetMarksUseCase
import com.papmobdev.domain.cars.usecasecarmodels.GetModelsUseCase
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.ObserveConfigurationCarUseCase
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.UpdateConfigurationCarUseCase
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.ParametersModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalCoroutinesApi
class CarParameterListViewModel(
    private val getMarksUseCase: GetMarksUseCase,
    private val getModelsUseCase: GetModelsUseCase,
    private val getGenerationsUseCase: GetGenerationsUseCase,
    private val observeConfigurationCarUseCase: ObserveConfigurationCarUseCase,
    private val updateConfigurationCarUseCase: UpdateConfigurationCarUseCase
) : ViewModel(), TextViewBindingAdapter.OnTextChanged {

    private val _listParameters: MutableLiveData<MutableList<*>?> = MutableLiveData()
    val listParameters: LiveData<MutableList<ParametersModel>?> = _listParameters.map {
        it?.convertCarsParametersToAdapter()
    }

    private val _listParametersCopy: MutableLiveData<MutableList<*>?> = MutableLiveData()
    val listParametersCopy: LiveData<MutableList<ParametersModel>?> = _listParametersCopy.map {
        it?.convertCarsParametersToAdapter()
    }

    lateinit var codeParametersCar: CodeParametersCar

    private lateinit var carConfiguration: CarConfiguration

    private val _showErrorMessage = MutableLiveData<String>()
    val showErrorMessage: LiveData<String> = _showErrorMessage

    private fun getMarks(): LiveData<Result<List<CarMark>>> =
        getMarksUseCase().asLiveData()

    private fun getModels(id: Int): LiveData<Result<List<CarModel>>> =
        getModelsUseCase(id).asLiveData()

    private fun getGenerations(id: Int): LiveData<Result<List<CarGeneration>>> =
        getGenerationsUseCase(id).asLiveData()

    private fun getLastConfiguration(): LiveData<Result<CarConfiguration>> =
        observeConfigurationCarUseCase().asLiveData()

    fun fetchData() {
        if (listParameters.value != null) return

        viewModelScope.launch(Dispatchers.IO) {
            getLastConfiguration().asFlow().collect { result ->
                result.onSuccess {
                    carConfiguration = it
                    getList(codeParametersCar)
                }
            }
        }
    }

    private suspend fun getList(typeOption: CodeParametersCar) {
        when (typeOption) {
            CodeParametersCar.MARK -> getMarks().asFlow().collect { result ->
                result.onSuccess {
                    initListsParameters(it)
                }
            }

            CodeParametersCar.MODEL -> carConfiguration.fkCarMark?.let { id ->
                getModels(id).asFlow()
                    .collect { result ->
                        result.onSuccess {
                            initListsParameters(it)
                        }
                    }
            }

            CodeParametersCar.GENERATION -> carConfiguration.fkCarModel?.let { id ->
                getGenerations(id).asFlow()
                    .collect { result ->
                        result.onSuccess {
                            initListsParameters(it)
                        }
                    }
            }
        }
    }

    private fun initListsParameters(it: List<*>?) {
        _listParameters.postValue(it?.toMutableList())
        _listParametersCopy.postValue(it?.toMutableList())
    }

    private fun updateList(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            filteredList(query)
        }
    }

    private fun updateListCopy(newList: MutableList<*>?) {
        _listParametersCopy.postValue(newList)
    }

    private fun filteredList(query: String) {
        val queryCopy = query.toLowerCase(Locale.getDefault())
        var newList: MutableList<*>?
        newList = _listParameters.value?.toMutableList()
        if (queryCopy.isNotEmpty()) {
            when (codeParametersCar) {
                CodeParametersCar.MARK -> {
                    newList = newList?.filter {
                        (it as CarMark).name?.toLowerCase(Locale.getDefault())
                            ?.contains(queryCopy) == true
                    }?.toMutableList()
                }
                CodeParametersCar.MODEL -> {
                    newList = newList?.filter {
                        (it as CarModel).name?.toLowerCase(Locale.getDefault())
                            ?.contains(queryCopy) == true
                    }?.toMutableList()
                }
                CodeParametersCar.GENERATION -> {
                    newList = newList?.filter {
                        (it as CarGeneration).name?.toLowerCase(Locale.getDefault())
                            ?.contains(queryCopy) == true
                    }?.toMutableList()
                }
            }
        }
        updateListCopy(newList)
    }

    fun updateConfiguration(typeOption: CodeParametersCar, item: ParametersModel) {
        viewModelScope.launch(Dispatchers.IO) {
            var newCarConfiguration: CarConfiguration? = null
            when (typeOption) {
                CodeParametersCar.MARK -> {
                    if (carConfiguration.fkCarMark != item.id)
                        newCarConfiguration = CarConfiguration(
                            fkCarMark = item.id,
                            nameMark = item.name
                        )
                }
                CodeParametersCar.MODEL -> {
                    if (carConfiguration.fkCarModel != item.id)
                        newCarConfiguration = CarConfiguration(
                            fkCarMark = carConfiguration.fkCarMark,
                            nameMark = carConfiguration.nameMark,
                            fkCarModel = item.id,
                            nameModel = item.name
                        )
                }
                CodeParametersCar.GENERATION -> {
                    if (carConfiguration.fkCarGeneration != item.id)
                        newCarConfiguration = CarConfiguration(
                            fkCarMark = carConfiguration.fkCarMark,
                            nameMark = carConfiguration.nameMark,
                            fkCarModel = carConfiguration.fkCarModel,
                            nameModel = carConfiguration.nameModel,
                            fkCarGeneration = item.id,
                            nameGeneration = item.name,
                        )
                }
            }
            if (newCarConfiguration != null) {
                newCarConfiguration.apply {
                    fkTypeFuel = carConfiguration.fkTypeFuel
                    engineVolume = carConfiguration.engineVolume
                    note = carConfiguration.note
                }
                updateConfigurationCarUseCase.execute(newCarConfiguration).collect { result ->
                    result.onSuccess {
                        if (!it) _showErrorMessage.postValue("Ошибка при обновлении конфигурации")
                    }
                }
            }
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        updateList(s.toString())
    }

    private fun <T> MutableList<T>.convertCarsParametersToAdapter(): MutableList<ParametersModel> {
        return this.map {
            when (it) {
                is CarMark -> ParametersModel(it.id, it.name)
                is CarModel -> ParametersModel(it.id, it.name)
                is CarGeneration -> ParametersModel(it.id, it.name)
                else -> throw Exception("Type not defined")
            }
        }.toMutableList()
    }
}