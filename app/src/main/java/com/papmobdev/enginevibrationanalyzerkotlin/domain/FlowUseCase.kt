package com.papmobdev.enginevibrationanalyzerkotlin.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

interface FlowUseCase<in P, R> {

    fun execute(param: P): Flow<Result<R>>

    fun <T> Flow<Result<T>>.handleOn(dispatcher: CoroutineDispatcher = Dispatchers.IO): Flow<Result<T>> {
        return this
                .catch { e -> emit(Result.failure(Exception(e))) }
                .flowOn(dispatcher)
    }

    operator fun invoke(param: P): Flow<Result<R>> = execute(param).handleOn()

}
