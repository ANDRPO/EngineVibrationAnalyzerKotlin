package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import androidx.lifecycle.*
import com.papmobdev.domain.cars.CodeOptionsCar
import com.papmobdev.domain.cars.models.CarGeneration
import com.papmobdev.domain.cars.models.CarMark
import com.papmobdev.domain.cars.models.CarModel
import com.papmobdev.domain.cars.usecasecargeneration.GetGenerationsUseCase
import com.papmobdev.domain.cars.usecasecarmarks.GetMarksUseCase
import com.papmobdev.domain.cars.usecasecarmodels.GetModelsUseCase
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.adapters.CarParametersAdapters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalCoroutinesApi
class CarParameterListViewModel(
    private val getMarksUseCase: GetMarksUseCase,
    private val getModelsUseCase: GetModelsUseCase,
    private val getGenerationsUseCase: GetGenerationsUseCase
) : ViewModel() {

    private var adapter: CarParametersAdapters<*>? = null
    private lateinit var listOptions: MutableList<*>
    private lateinit var listOptionsCopy: MutableList<*>

    private fun getMarks(): LiveData<Result<List<CarMark>>> = getMarksUseCase()
        .asLiveData()

    private fun getModels(id: Int): LiveData<Result<List<CarModel>>> = getModelsUseCase(id)
        .asLiveData()

    private fun getGenerations(id: Int): LiveData<Result<List<CarGeneration>>> = getGenerationsUseCase(id)
        .asLiveData()

    fun fetchData(): LiveData<List<*>>{

    }

    fun setTypeCarOption(carType: CodeOptionsCar, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (carType) {
                CodeOptionsCar.MARK -> getMarks().asFlow().collect {
                    setListOptions(it)
                }
                CodeOptionsCar.MODEL -> getModels(id).asFlow().collect {
                    setListOptions(it)
                }
                CodeOptionsCar.GENERATION -> getGenerations(id).asFlow().collect {
                    setListOptions(it)
                }
            }
        }
    }

    private fun setListOptions(it: Result<List<*>>) {
        when {
            it.isSuccess -> {
                listOptions = it.getOrNull()?.toMutableList() ?: mutableListOf("Ничего не найдено")
            }
            it.isFailure -> listOptions = mutableListOf("Ничего не найдено")

        }
        listOptionsCopy = listOptions.toMutableList()
    }

    fun filterSearch(filter: String) {
        val paramFilter = filter.toLowerCase(Locale.getDefault())
        listOptions.clear()
        if (paramFilter.isEmpty()) {
            listOptions.addAll()
        } else {
            for (str in ) {
                if (str.name.toLowerCase(Locale.getDefault()).contains(filter)) {
                    items.add(str)
                }
            }
        }
        notifyDataSetChanged()

    }


}