package com.papmobdev.domain.diagnostic.models

import com.papmobdev.domain.sensor.models.EventModel

data class SendDiagnosticAndEventsModel(
    val diagnosticModel: DiagnosticModel,
    val listEvents: List<EventModel>
)
