package com.papmobdev.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

interface BiFlowUseCase<in F, in S, R> {
    fun execute(firstParam: F, secondParam: S): Flow<Result<R>>

    fun <T> Flow<Result<T>>.handleOn(dispatcher: CoroutineDispatcher = Dispatchers.IO): Flow<Result<T>> {
        return this
            .catch { e -> emit(Result.failure(Exception(e))) }
            .flowOn(dispatcher)
    }

    operator fun invoke(firstParam: F, secondParam: S): Flow<Result<R>> =
        execute(firstParam, secondParam).handleOn()
}