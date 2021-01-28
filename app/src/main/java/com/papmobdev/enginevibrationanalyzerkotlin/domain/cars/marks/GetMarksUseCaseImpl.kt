package com.papmobdev.enginevibrationanalyzerkotlin.domain.cars.marks

import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.CarMarkEntity
import com.papmobdev.enginevibrationanalyzerkotlin.domain.cars.CarsDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class GetMarksUseCaseImpl(
        private val carsDataSource: CarsDataSource
) : GetMarksUseCase {
    override fun execute(): Flow<Result<List<CarMarkEntity>>> = flow {
        val marksData = carsDataSource.getMarks()
        emit(Result.success(marksData))
    }
}