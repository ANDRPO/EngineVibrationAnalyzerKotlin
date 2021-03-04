package com.papmobdev.data.database

import com.papmobdev.data.mapping.toData
import com.papmobdev.data.mapping.toDomain
import com.papmobdev.domain.diagnostic.DiagnosticDataSource
import com.papmobdev.domain.diagnostic.models.DiagnosticModel
import com.papmobdev.domain.sensor.models.EventModel

class DiagnosticDataSourceImpl(
    private val dao: AppDataBaseDao
) : DiagnosticDataSource {

    override fun sendEvents(idDiagnostic: Long, list: List<EventModel>) =
        dao.insertSensorEvents(list.map {
            it.toData(idDiagnostic)
        })

    override fun sendDiagnostic(diagnostic: DiagnosticModel) =
        dao.insertDiagnostic(diagnostic.toData())

    override fun sendDiagnosticAndEventsData(diagnostic: DiagnosticModel, list: List<EventModel>) =
        dao.insertDiagnosticEventsTransaction(diagnostic.toData(), list)


    override fun getLastDiagnostic(): DiagnosticModel = dao.getLastDiagnostic().toDomain()
}