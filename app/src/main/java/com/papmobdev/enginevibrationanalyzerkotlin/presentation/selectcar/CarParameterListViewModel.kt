package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

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
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.GetConfigurationCarUseCase
import com.papmobdev.domain.cars.usecaseslastconfigurationcar.UpdateConfigurationCarUseCase
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.SearchFilterDiffUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalCoroutinesApi
class CarParameterListViewModel(
    private val getMarksUseCase: GetMarksUseCase,
    private val getModelsUseCase: GetModelsUseCase,
    private val getGenerationsUseCase: GetGenerationsUseCase,
    private val getConfigurationCarUseCase: GetConfigurationCarUseCase,
    private val updateConfigurationCarUseCase: UpdateConfigurationCarUseCase
) : ViewModel(), TextViewBindingAdapter.OnTextChanged {

    private val _listOptions: MutableLiveData<MutableList<*>> = MutableLiveData()
    val listOptions: LiveData<MutableList<*>> = _listOptions

    private val _listOptionsCopy: MutableLiveData<MutableList<*>> = MutableLiveData()
    val listOptionsCopy: LiveData<MutableList<*>> = _listOptionsCopy

    private val _diffResult: MutableLiveData<DiffUtil.DiffResult> = MutableLiveData()
    val diffResult: LiveData<DiffUtil.DiffResult> = _diffResult

    lateinit var carOptionsCar: CodeOptionsCar

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
        if (listOptions.value != null) return
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

    private suspend fun getList(typeOption: CodeOptionsCar) {
        when (typeOption) {
            CodeOptionsCar.MARK -> getMarks().asFlow().collect { result ->
                result.onSuccess {
                    initListsOptions(it)
                }
                result.onFailure {
                    TODO("Add Exception")
                }
            }

            CodeOptionsCar.MODEL -> lastCarConfiguration.fkCarMark?.let { id ->
                getModels(id).asFlow()
                    .collect { result ->
                        result.onSuccess {
                            initListsOptions(it)
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
                            initListsOptions(it)
                        }
                        result.onFailure {
                            TODO("Add Exception")
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
        val oldList = _listOptionsCopy.value?.toMutableList()

        updateListCopy(newList)
        notifyDiffResult(newList?.toList(), oldList)
    }

    private fun notifyDiffResult(newList: List<*>?, oldList: List<*>?) {
        _diffResult.postValue(
            DiffUtil.calculateDiff(
                SearchFilterDiffUtils(
                    newList,
                    oldList
                )
            )
        )
    }

    private fun updateListCopy(newList: MutableList<*>?) {
        _listOptionsCopy.postValue(newList)
    }


    fun <T> updateConfiguration(typeOption: CodeOptionsCar, item: T) {
        viewModelScope.launch(Dispatchers.IO) {
            var newCarConfiguration: LastCarConfigurationModel? = null
            when (typeOption) {
                CodeOptionsCar.MARK -> {
                    if (lastCarConfiguration.fkCarMark != (item as CarMark).id)
                        newCarConfiguration = LastCarConfigurationModel(
                            fkCarMark = (item as CarMark).id,
                            nameMark = (item as CarMark).name
                        )
                }
                CodeOptionsCar.MODEL -> {
                    if (lastCarConfiguration.fkCarModel != (item as CarModel).id)
                        newCarConfiguration = LastCarConfigurationModel(
                            fkCarMark = lastCarConfiguration.fkCarMark,
                            nameMark = lastCarConfiguration.nameMark,
                            fkCarModel = (item as CarModel).id,
                            nameModel = (item as CarModel).name
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
                newCarConfiguration.apply {
                    fkTypeFuel = lastCarConfiguration.fkTypeFuel
                    engineVolume = lastCarConfiguration.engineVolume
                    note = lastCarConfiguration.note
                }
                updateConfigurationCarUseCase.execute(newCarConfiguration).collect()
            }
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        updateList(s.toString())
    }
}