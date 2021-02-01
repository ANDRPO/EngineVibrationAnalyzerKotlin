package com.papmobdev.domain.cars.usecasecargeneration

import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.models.CarGeneration
import com.papmobdev.domain.cars.models.CarModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class GetGenerationsUseCaseImpl(
        private val carsDataSource: CarsDataSource
) : GetGenerationsUseCase {
    override fun execute(param: CarModel): Flow<Result<List<CarGeneration>>> = flow {
        val generationsData = param.id.let {
            carsDataSource.getGenerations(it)
        }
        emit(Result.success(generationsData))
    }
}