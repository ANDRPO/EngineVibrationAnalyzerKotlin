package com.papmobdev.domain.cars.usecasecarmodels

import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.models.CarMark
import com.papmobdev.domain.cars.models.CarModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class GetModelsUseCaseImpl(
    private val carsDataSource: CarsDataSource
) : GetModelsUseCase {
    override fun execute(param: Int): Flow<Result<List<CarModel>>> = flow {
        val modelsData = carsDataSource.getModels(param)
        emit(Result.success(modelsData))
    }
}