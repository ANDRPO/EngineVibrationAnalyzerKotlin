package com.papmobdev.enginevibrationanalyzerkotlin.domain.cars.generation

import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.CarGenerationEntity
import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.CarModelEntity
import com.papmobdev.enginevibrationanalyzerkotlin.domain.cars.CarsDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class GetGenerationsUseCaseImpl(
        private val carsDataSource: CarsDataSource
) : GetGenerationsUseCase {
    override fun execute(param: CarModelEntity): Flow<Result<List<CarGenerationEntity>>> = flow {
        val generationsData = param.idCarModel.let {
            carsDataSource.getGenerations(it)
        }
        emit(Result.success(generationsData))
    }
}