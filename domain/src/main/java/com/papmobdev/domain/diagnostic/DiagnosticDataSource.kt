package com.papmobdev.domain.diagnostic

import com.papmobdev.domain.diagnostic.models.DiagnosticModel
import com.papmobdev.domain.sensor.models.EventModel

interface DiagnosticDataSource {
    fun sendEvents(idDiagnostic: Long, list: List<EventModel>)
    fun sendDiagnostic(diagnostic: DiagnosticModel): Long
    fun sendDiagnosticAndEventsData(diagnostic: DiagnosticModel, list: List<EventModel>)
    fun getLastDiagnostic(): DiagnosticModel
}