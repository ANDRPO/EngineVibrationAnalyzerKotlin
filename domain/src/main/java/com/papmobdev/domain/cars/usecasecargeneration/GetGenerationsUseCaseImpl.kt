package com.papmobdev.domain.cars.usecasecargeneration

import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.models.CarGeneration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class GetGenerationsUseCaseImpl(
    private val carsDataSource: CarsDataSource
) : GetGenerationsUseCase {
    override fun execute(param: Int): Flow<Result<List<CarGeneration>>> = try {
        flow {
            val generationsData = param.let {
                carsDataSource.getGenerations(it)
            }
            emit(Result.success(generationsData))
        }
    } catch (e: Exception) {
        flow {
            emit(Result.success(listOf<CarGeneration>()))
        }
    }
}