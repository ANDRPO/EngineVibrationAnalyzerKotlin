package com.papmobdev.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

interface CallbackFlow<R> {
    fun execute(): Flow<R>

    fun <T> Flow<T>.handleOn(dispatcher: CoroutineDispatcher = Dispatchers.Default): Flow<T> {
        return this
            .flowOn(dispatcher)
    }

    operator fun invoke(): Flow<R> = execute().handleOn()
}