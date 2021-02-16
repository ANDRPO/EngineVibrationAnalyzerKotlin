package com.papmobdev.domain.cars.usecasecarmarks

import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.models.CarMark
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

@ExperimentalCoroutinesApi
class GetMarksUseCaseImpl(
    private val carsDataSource: CarsDataSource
) : GetMarksUseCase {
    override fun execute(): Flow<Result<List<CarMark>>> = try {
        flow {
            val marksData = carsDataSource.getMarks()
            emit(Result.success(marksData))
        }
    } catch (e: Exception) {
        flow {
            emit(Result.success(listOf<CarMark>()))
        }
    }
}