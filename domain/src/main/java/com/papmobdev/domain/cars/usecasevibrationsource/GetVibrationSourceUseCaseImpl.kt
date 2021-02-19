package com.papmobdev.domain.cars.usecasevibrationsource

import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.models.VibrationSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class GetVibrationSourceUseCaseImpl(
    private val carsDataSource: CarsDataSource
) : GetVibrationSourceUseCase {
    @ExperimentalCoroutinesApi
    override fun execute(): Flow<Result<List<VibrationSource>>> = flow {
        val typesVibrationSource = carsDataSource.getVibrationSource()
        emit(Result.success(typesVibrationSource))
    }
}