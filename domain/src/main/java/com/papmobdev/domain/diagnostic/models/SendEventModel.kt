package com.papmobdev.domain.diagnostic.models

import com.papmobdev.domain.sensor.models.EventModel

data class SendEventModel(
    val idDiagnostic: Long,
    val listEvents: List<EventModel>
)
