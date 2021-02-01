package com.papmobdev.domain.tests

import com.papmobdev.domain.tests.models.TestModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class GetTestsUseCaseImpl: GetTestsUseCase {
    override fun execute(): Flow<Result<List<TestModel>>> {
        TODO("Not yet implemented")
    }

}