package com.papmobdev.domain.cars.usecasetypesfuels

import com.papmobdev.domain.cars.CarsDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class GetTypesFuelUseCaseImpl(
    private val carsDataSource: CarsDataSource
) : GetTypesFuelUseCase {
    override fun execute(): Flow<Result<List<TypeFuel>>> = flow {
        val typesFuel = carsDataSource.getTypesFuel()
        emit(Result.success(typesFuel))
    }
}