package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.selectconfigurationcar

import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.lifecycle.*
import com.papmobdev.domain.cars.CodeOptionsCar
import com.papmobdev.domain.cars.models.CarGeneration
import com.papmobdev.domain.cars.models.CarMark
import com.papmobdev.domain.cars.models.CarModel
import com.papmobdev.domain.cars.models.CarConfiguration
import com.papmobdev.domain.cars.usecasecargeneration.GetGenerationsUseCase
import com.papmobdev.domain.cars.usecasecarmarks.GetMarksUseCase
import com.papmobdev.domain.cars.usecasecarmodels.GetModelsUseCase
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.ObserveConfigurationCarUseCase
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.UpdateConfigurationCarUseCase
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.OptionsModel
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

    private val _listOptions: MutableLiveData<MutableList<*>> = MutableLiveData()
    val listOptions: LiveData<MutableList<OptionsModel>> = _listOptions.map {
        it.convertCarsOptionsToAdapter()
    }

    private val _listOptionsCopy: MutableLiveData<MutableList<*>> = MutableLiveData()
    val listOptionsCopy: LiveData<MutableList<OptionsModel>> = _listOptionsCopy.map {
        it.convertCarsOptionsToAdapter()
    }

    lateinit var carOptionsCar: CodeOptionsCar

    private lateinit var carConfiguration: CarConfiguration

    private fun getMarks(): LiveData<Result<List<CarMark>>> =
        getMarksUseCase().asLiveData()

    private fun getModels(id: Int): LiveData<Result<List<CarModel>>> =
        getModelsUseCase(id).asLiveData()

    private fun getGenerations(id: Int): LiveData<Result<List<CarGeneration>>> =
        getGenerationsUseCase(id).asLiveData()

    private fun getLastConfiguration(): LiveData<Result<CarConfiguration>> =
        observeConfigurationCarUseCase().asLiveData()

    private val _showErrorMessage = MutableLiveData<String>()
    val showErrorMessage = _showErrorMessage

    fun fetchData(typeOption: CodeOptionsCar) {
        if (listOptions.value != null) return
        viewModelScope.launch(Dispatchers.IO) {
            getLastConfiguration().asFlow().collect { result ->
                result.onSuccess {
                    carConfiguration = it
                    getList(typeOption)
                }
            }
        }
    }

    private suspend fun getList(typeOption: CodeOptionsCar) {
        when (typeOption) {
            CodeOptionsCar.MARK -> getMarks().asFlow().collect { result ->
                result.onSuccess {
                    initListsOptions(it)
                }
            }

            CodeOptionsCar.MODEL -> carConfiguration.fkCarMark?.let { id ->
                getModels(id).asFlow()
                    .collect { result ->
                        result.onSuccess {
                            initListsOptions(it)
                        }
                    }
            }

            CodeOptionsCar.GENERATION -> carConfiguration.fkCarModel?.let { id ->
                getGenerations(id).asFlow()
                    .collect { result ->
                        result.onSuccess {
                            initListsOptions(it)
                        }
                    }
            }
        }
    }

    private fun initListsOptions(it: List<*>?) {
        _listOptions.postValue(it?.toMutableList())
        _listOptionsCopy.postValue(it?.toMutableList())
    }

    private fun updateList(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            filteredList(query)
        }
    }

    private fun filteredList(query: String) {
        val queryCopy = query.toLowerCase(Locale.getDefault())
        var newList: MutableList<*>?
        newList = _listOptions.value?.toMutableList()
        if (queryCopy.isNotEmpty()) {
            when (carOptionsCar) {
                CodeOptionsCar.MARK -> {
                    newList = newList?.filter {
                        (it as CarMark).name?.toLowerCase(Locale.getDefault())
                            ?.contains(queryCopy) == true
                    }?.toMutableList()
                }
                CodeOptionsCar.MODEL -> {
                    newList = newList?.filter {
                        (it as CarModel).name?.toLowerCase(Locale.getDefault())
                            ?.contains(queryCopy) == true
                    }?.toMutableList()
                }
                CodeOptionsCar.GENERATION -> {
                    newList = newList?.filter {
                        (it as CarGeneration).name?.toLowerCase(Locale.getDefault())
                            ?.contains(queryCopy) == true
                    }?.toMutableList()
                }
            }
        }
        updateListCopy(newList)
    }


    private fun updateListCopy(newList: MutableList<*>?) {
        _listOptionsCopy.postValue(newList)
    }

    fun updateConfiguration(typeOption: CodeOptionsCar, item: OptionsModel) {
        viewModelScope.launch(Dispatchers.IO) {
            var newCarConfiguration: CarConfiguration? = null
            when (typeOption) {
                CodeOptionsCar.MARK -> {
                    if (carConfiguration.fkCarMark != item.id)
                        newCarConfiguration = CarConfiguration(
                            fkCarMark = item.id,
                            nameMark = item.name
                        )
                }
                CodeOptionsCar.MODEL -> {
                    if (carConfiguration.fkCarModel != item.id)
                        newCarConfiguration = CarConfiguration(
                            fkCarMark = carConfiguration.fkCarMark,
                            nameMark = carConfiguration.nameMark,
                            fkCarModel = item.id,
                            nameModel = item.name
                        )
                }
                CodeOptionsCar.GENERATION -> {
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

    private fun <T> MutableList<T>.convertCarsOptionsToAdapter(): MutableList<OptionsModel> {
        return this.map {
            when (it) {
                is CarMark -> OptionsModel(it.id, it.name)
                is CarModel -> OptionsModel(it.id, it.name)
                is CarGeneration -> OptionsModel(it.id, it.name)
                else -> throw Exception("Type not defined")
            }
        }.toMutableList()
    }
}