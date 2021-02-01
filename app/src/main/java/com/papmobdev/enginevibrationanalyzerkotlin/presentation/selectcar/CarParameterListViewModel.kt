package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.papmobdev.domain.cars.models.CarMark
import com.papmobdev.domain.cars.usecasecargeneration.GetGenerationsUseCase
import com.papmobdev.domain.cars.usecasecarmarks.GetMarksUseCase
import com.papmobdev.domain.cars.usecasecarmodels.GetModelsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class CarParameterListViewModel(
    private val getMarksUseCase: GetMarksUseCase,
    private val getModelsUseCase: GetModelsUseCase,
    private val getGenerationsUseCase: GetGenerationsUseCase
) : ViewModel(){

    var listOptions: LiveData<Result<List<CarMark>>> = getMark()

    fun getMark(): LiveData<Result<List<CarMark>>> = getMarksUseCase()
        .asLiveData()

}