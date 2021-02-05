package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import androidx.lifecycle.*
import androidx.recyclerview.widget.DiffUtil
import com.papmobdev.domain.cars.CodeOptionsCar
import com.papmobdev.domain.cars.models.CarGeneration
import com.papmobdev.domain.cars.models.CarMark
import com.papmobdev.domain.cars.models.CarModel
import com.papmobdev.domain.cars.models.LastCarConfigurationModel
import com.papmobdev.domain.cars.usecasecargeneration.GetGenerationsUseCase
import com.papmobdev.domain.cars.usecasecarmarks.GetMarksUseCase
import com.papmobdev.domain.cars.usecasecarmodels.GetModelsUseCase
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.GetConfigurationCarUseCase
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.SearchFilterDiffUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class CarParameterListViewModel(
    private val getMarksUseCase: GetMarksUseCase,
    private val getModelsUseCase: GetModelsUseCase,
    private val getGenerationsUseCase: GetGenerationsUseCase,
    private val getConfigurationCarUseCase: GetConfigurationCarUseCase
) : ViewModel() {

    private lateinit var _listOptions: MutableLiveData<List<*>>
    val listOptions: LiveData<List<*>> = _listOptions
    private lateinit var _listOptionsCopy: List<*>
    val listOptionsCopy: List<*> = _listOptionsCopy

    private lateinit var diffResult: LiveData<DiffUtil.DiffResult>

    private lateinit var lastCarConfiguration: LastCarConfigurationModel

    private fun getMarks(): LiveData<Result<List<CarMark>>> =
        getMarksUseCase().asLiveData()

    private fun getModels(id: Int?): LiveData<Result<List<CarModel>>> =
        getModelsUseCase(id).asLiveData()

    private fun getGenerations(id: Int?): LiveData<Result<List<CarGeneration>>> =
        getGenerationsUseCase(id).asLiveData()

    private fun getLastConfiguration(): LiveData<Result<LastCarConfigurationModel>> =
        getConfigurationCarUseCase().asLiveData()

    fun fetchData(typeOption: CodeOptionsCar) {
        viewModelScope.launch(Dispatchers.IO) {
            getLastConfiguration().asFlow().collect { result ->
                result.onSuccess {
                    lastCarConfiguration = it
                }
                result.onFailure {
                    TODO("Add exception")
                }
            }

            when (typeOption) {
                CodeOptionsCar.MARK -> getMarks().asFlow().collect { result ->
                    result.onSuccess {
                        _listOptions.value = it
                        _listOptionsCopy = it.toList()
                    }
                    result.onFailure {
                        TODO("Add Exception")
                    }
                }

                CodeOptionsCar.MODEL -> getModels(lastCarConfiguration.fkCarMark).asFlow()
                    .collect { result ->
                        result.onSuccess {
                            _listOptions.value = it
                            _listOptionsCopy = it.toList()
                        }
                        result.onFailure {
                            TODO("Add Exception")
                        }
                    }

                CodeOptionsCar.GENERATION -> getGenerations(lastCarConfiguration.fkCarModel).asFlow()
                    .collect { result ->
                        result.onSuccess {
                            _listOptions.value = it
                            _listOptionsCopy = it.toList()
                        }
                        result.onFailure {
                            TODO("Add Exception")
                        }
                    }
            }
        }
    }



    fun updateList() {
        val diffResult = DiffUtil.calculateDiff(SearchFilterDiffUtils(listOptionsCopy, listOptionsCopy))
    }
}