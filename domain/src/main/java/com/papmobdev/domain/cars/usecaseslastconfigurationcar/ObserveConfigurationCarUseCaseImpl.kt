package com.papmobdev.domain.cars.usecaseslastconfigurationcar

import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.models.CarConfiguration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class ObserveConfigurationCarUseCaseImpl(
    private val carsDataSource: CarsDataSource
) : ObserveConfigurationCarUseCase {
    override fun execute(): Flow<Result<CarConfiguration>> = try {
        carsDataSource.getLastCarConfiguration().map {
            Result.success(it)
        }
    } catch (e: Exception) {
        flow {
            Result.success(CarConfiguration())
        }
    }
}