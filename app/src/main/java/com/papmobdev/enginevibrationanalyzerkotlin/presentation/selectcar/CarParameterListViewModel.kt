package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import android.util.Log
import androidx.databinding.adapters.TextViewBindingAdapter
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
import com.papmobdev.domain.cars.usecasesearchfilter.GetFilteredListUseCase
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.GetConfigurationCarUseCase
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.UpdateConfigurationCarUseCase
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
    private val getConfigurationCarUseCase: GetConfigurationCarUseCase,
    private val updateConfigurationCarUseCase: UpdateConfigurationCarUseCase,
    private val getFilteredListUseCase: GetFilteredListUseCase
) : ViewModel(), TextViewBindingAdapter.OnTextChanged {

    private val _listOptions: MutableLiveData<MutableList<*>> = MutableLiveData()
    val listOptions: LiveData<MutableList<*>> = _listOptions

    private val _listOptionsCopy: MutableLiveData<MutableList<*>> = MutableLiveData()
    val listOptionsCopy: LiveData<MutableList<*>> = _listOptionsCopy

    private val _diffResult: MutableLiveData<DiffUtil.DiffResult> = MutableLiveData()
    val diffResult: LiveData<DiffUtil.DiffResult> = _diffResult

    private lateinit var lastCarConfiguration: LastCarConfigurationModel

    private fun getMarks(): LiveData<Result<List<CarMark>>> =
        getMarksUseCase().asLiveData()

    private fun getModels(id: Int): LiveData<Result<List<CarModel>>> =
        getModelsUseCase(id).asLiveData()

    private fun getGenerations(id: Int): LiveData<Result<List<CarGeneration>>> =
        getGenerationsUseCase(id).asLiveData()

    private fun getLastConfiguration(): LiveData<Result<LastCarConfigurationModel>> =
        getConfigurationCarUseCase().asLiveData()

    fun fetchData(typeOption: CodeOptionsCar) {
        Log.d("OPTIONTYPE", typeOption.name)
        viewModelScope.launch(Dispatchers.IO) {
            getLastConfiguration().asFlow().collect { result ->
                result.onSuccess {
                    lastCarConfiguration = it
                    getList(typeOption)
                }
                result.onFailure {
                    TODO("Add exception")
                }
            }
        }
    }

    private fun getList(typeOption: CodeOptionsCar) {
        viewModelScope.launch(Dispatchers.Default) {
            when (typeOption) {
                CodeOptionsCar.MARK -> getMarks().asFlow().collect { result ->
                    result.onSuccess {
                        _listOptions.postValue(it.toMutableList())
                        _listOptionsCopy.postValue(it.toMutableList())
                    }
                    result.onFailure {
                        TODO("Add Exception")
                    }
                }

                CodeOptionsCar.MODEL -> lastCarConfiguration.fkCarMark?.let { id ->
                    getModels(id).asFlow()
                        .collect { result ->
                            result.onSuccess {
                                _listOptions.postValue(it.toMutableList())
                                _listOptionsCopy.postValue(it.toMutableList())
                            }
                            result.onFailure {
                                TODO("Add Exception")
                            }
                        }
                }

                CodeOptionsCar.GENERATION -> lastCarConfiguration.fkCarModel?.let { id ->
                    getGenerations(id).asFlow()
                        .collect { result ->
                            result.onSuccess {
                                _listOptions.postValue(it.toMutableList())
                                _listOptionsCopy.postValue(it.toMutableList())
                            }
                            result.onFailure {
                                TODO("Add Exception")
                            }
                        }
                }
            }
        }
    }

    fun updateList(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _listOptions.value?.let {
                getFilteredListUseCase.execute(query, it).collect { result ->
                    result.onSuccess { newList ->
                        _diffResult.postValue(
                            DiffUtil.calculateDiff(
                                SearchFilterDiffUtils(
                                    newList, _listOptionsCopy.value
                                )
                            )
                        )
                        _listOptionsCopy.postValue(newList)
                    }
                    result.onFailure {
                        TODO("Add exception")
                    }
                }
            }
        }
    }

    fun <T> updateConfiguration(typeOption: CodeOptionsCar, item: T) {
        viewModelScope.launch(Dispatchers.IO) {
            var newCarConfiguration: LastCarConfigurationModel? = null
            when (typeOption) {
                CodeOptionsCar.MARK -> {
                    if (lastCarConfiguration.fkCarMark != (item as CarMark).id)
                        newCarConfiguration = LastCarConfigurationModel(
                            fkCarMark = (item as CarMark).id,
                            nameMark = (item as CarMark).name,
                            fkCarModel = null,
                            nameModel = null,
                            fkCarGeneration = null,
                            nameGeneration = null
                        )
                }
                CodeOptionsCar.MODEL -> {
                    if (lastCarConfiguration.fkCarModel != (item as CarModel).id)
                        newCarConfiguration = LastCarConfigurationModel(
                            fkCarMark = lastCarConfiguration.fkCarMark,
                            nameMark = lastCarConfiguration.nameMark,
                            fkCarModel = (item as CarModel).id,
                            nameModel = (item as CarModel).name,
                            fkCarGeneration = null,
                            nameGeneration = null
                        )
                }
                CodeOptionsCar.GENERATION -> {
                    if (lastCarConfiguration.fkCarGeneration != (item as CarGeneration).id)
                        newCarConfiguration = LastCarConfigurationModel(
                            fkCarMark = lastCarConfiguration.fkCarMark,
                            nameMark = lastCarConfiguration.nameMark,
                            fkCarModel = lastCarConfiguration.fkCarModel,
                            nameModel = lastCarConfiguration.nameModel,
                            fkCarGeneration = (item as CarGeneration).id,
                            nameGeneration = (item as CarGeneration).name,
                        )
                }
            }
            if (newCarConfiguration != null) {
                updateConfigurationCarUseCase.execute(newCarConfiguration).collect{
                    it.onSuccess {
                        Log.e("UPDATECONF", "SUCCESS")
                    }
                    it.onFailure {
                        Log.e("UPDATECONF", "FAIL")
                    }
                }
            }
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        updateList(s.toString())
    }
}