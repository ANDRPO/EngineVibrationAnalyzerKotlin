package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import androidx.lifecycle.*
import com.papmobdev.domain.cars.CodeOptionsCar
import com.papmobdev.domain.cars.models.CarGeneration
import com.papmobdev.domain.cars.models.CarMark
import com.papmobdev.domain.cars.models.CarModel
import com.papmobdev.domain.cars.usecasecargeneration.GetGenerationsUseCase
import com.papmobdev.domain.cars.usecasecarmarks.GetMarksUseCase
import com.papmobdev.domain.cars.usecasecarmodels.GetModelsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class CarParameterListViewModel(
    private val getMarksUseCase: GetMarksUseCase,
    private val getModelsUseCase: GetModelsUseCase,
    private val getGenerationsUseCase: GetGenerationsUseCase
) : ViewModel() {

    fun getMarks(): LiveData<Result<List<CarMark>>> = getMarksUseCase()
        .asLiveData()

    fun getModels(id: Int): LiveData<Result<List<CarModel>>> = getModelsUseCase(id)
        .asLiveData()

    fun getGenerations(id: Int): LiveData<Result<List<CarGeneration>>> = getGenerationsUseCase(id)
        .asLiveData()

}