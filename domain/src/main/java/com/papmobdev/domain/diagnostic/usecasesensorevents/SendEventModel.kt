package com.papmobdev.domain.diagnostic.usecasesensorevents

import com.papmobdev.domain.sensor.models.EventModel

data class SendEventModel(
    val idDiagnostic: Int,
    val listEvents: List<EventModel>
)
