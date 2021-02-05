package com.papmobdev.domain.cars.usecaseslastconfigurationcar

import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.models.LastCarConfigurationModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class SetConfigurationCarUseCaseImpl(
    private val carsDataSource: CarsDataSource
) : SetConfigurationCarUseCase {
    override fun execute(param: LastCarConfigurationModel): Flow<Result<Boolean>> = flow {
        carsDataSource.updateLastCarConfiguration(param)
        emit(Result.success(true))
    }

}
