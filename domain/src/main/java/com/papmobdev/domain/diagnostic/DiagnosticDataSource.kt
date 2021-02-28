package com.papmobdev.domain.diagnostic

import com.papmobdev.domain.diagnostic.models.DiagnosticModel
import com.papmobdev.domain.sensor.models.EventModel

interface DiagnosticDataSource {
    fun sendEvents(idDiagnostic: Int, list: List<EventModel>)
    fun sendDiagnostic(diagnostic: DiagnosticModel)
    fun sendDiagnosticAndEventsData(diagnostic: DiagnosticModel, list: List<EventModel>)
    fun getLastDiagnostic(): DiagnosticModel
}