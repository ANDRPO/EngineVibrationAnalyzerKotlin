package com.papmobdev.domain.diagnostic.interactor

import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.flow.Flow

interface InteractorDiagnostic {
    fun writeDiagnostic(listEvents: List<EventModel>): Flow<Result<Boolean>>
}