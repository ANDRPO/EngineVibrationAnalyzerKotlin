package com.papmobdev.domain.diagnostics.usecaseobservesensor

import com.papmobdev.domain.diagnostics.models.EventModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class ObserveSensorUseCaseImpl: ObserveSensorUseCase {
    override fun execute(): Flow<Result<EventModel>> {

    }

}