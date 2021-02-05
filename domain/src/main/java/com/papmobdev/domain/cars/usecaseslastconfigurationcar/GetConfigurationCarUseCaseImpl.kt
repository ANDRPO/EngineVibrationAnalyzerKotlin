package com.papmobdev.domain.cars.usecaseslastconfigurationcar

import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.models.LastCarConfigurationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetConfigurationCarUseCaseImpl(
    private val carsDataSource: CarsDataSource
) :GetConfigurationCarUseCase{
    override fun execute(): Flow<Result<LastCarConfigurationModel>> = flow {
        val modelConfiguration = carsDataSource.getLastCarConfiguration()
        emit(Result.success(modelConfiguration))
    }
}