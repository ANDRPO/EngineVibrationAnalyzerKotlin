package com.papmobdev.domain.diagnostic.usecasediagnosticdata

import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.flow.Flow

interface SendDiagnosticDataUseCase {
    fun writeDiagnostic(listEvents: List<EventModel>): Flow<Result<Boolean>>
}