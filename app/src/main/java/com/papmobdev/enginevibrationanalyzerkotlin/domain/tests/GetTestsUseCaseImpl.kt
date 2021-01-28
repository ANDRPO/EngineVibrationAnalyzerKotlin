package com.papmobdev.enginevibrationanalyzerkotlin.domain.tests

import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.TestEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class GetTestsUseCaseImpl: GetTestsUseCase {
    override fun execute(): Flow<Result<List<TestEntity>>> {
        TODO("Not yet implemented")
    }

}