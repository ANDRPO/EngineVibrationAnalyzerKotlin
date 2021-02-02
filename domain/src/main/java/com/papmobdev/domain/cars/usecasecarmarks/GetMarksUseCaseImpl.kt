package com.papmobdev.domain.cars.usecasecarmarks

import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.models.CarMark
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class GetMarksUseCaseImpl(
    private val carsDataSource: CarsDataSource
) : GetMarksUseCase {
    override fun execute(): Flow<Result<List<CarMark>>> = flow {
        val marksData = carsDataSource.getMarks()
        emit(Result.success(marksData))
    }
}