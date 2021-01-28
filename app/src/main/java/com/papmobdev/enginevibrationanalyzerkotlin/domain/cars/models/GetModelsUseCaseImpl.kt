package com.papmobdev.enginevibrationanalyzerkotlin.domain.cars.models

import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.CarMarkEntity
import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.CarModelEntity
import com.papmobdev.enginevibrationanalyzerkotlin.domain.cars.CarsDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class GetModelsUseCaseImpl(
        private val carsDataSource: CarsDataSource
) : GetModelsUseCase {
    override fun execute(param: CarMarkEntity): Flow<Result<List<CarModelEntity>>> = flow {
        val modelsData = param.idCarMark?.let {
            carsDataSource.getModels(it)
        }!!
        emit(Result.success(modelsData))
    }
}