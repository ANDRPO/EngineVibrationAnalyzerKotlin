package com.papmobdev.domain.cars.usecaseslastconfigurationcar

import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.models.CarConfiguration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class UpdateConfigurationCarUseCaseImpl(
    private val carsDataSource: CarsDataSource
) : UpdateConfigurationCarUseCase {
    override fun execute(param: CarConfiguration): Flow<Result<Boolean>> = try {
        flow {
            val result = carsDataSource.updateLastCarConfiguration(param)
            emit(Result.success(result))
        }
    } catch (e: Exception) {
        flow {
            emit(Result.success(false))
        }
    }

}
