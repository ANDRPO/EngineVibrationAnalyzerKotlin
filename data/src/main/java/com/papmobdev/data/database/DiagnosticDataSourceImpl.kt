package com.papmobdev.data.database

import com.papmobdev.data.database.entities.diagnostic.DiagnosticsEntity
import com.papmobdev.data.database.entities.diagnostic.SensorEventEntity
import com.papmobdev.domain.diagnostic.DiagnosticDataSource
import com.papmobdev.domain.diagnostic.models.DiagnosticModel
import com.papmobdev.domain.sensor.models.EventModel

class DiagnosticDataSourceImpl(
    private val dao: AppDataBaseDao
) : DiagnosticDataSource {

    override fun sendEvents(idDiagnostic: Int, list: List<EventModel>) =
        dao.insertSensorEvents(list.map {
            it.toData(idDiagnostic)
        })

    override fun sendDiagnostic(diagnostic: DiagnosticModel) =
        dao.insertDiagnostic(diagnostic.toData())


    override fun getListDiagnostic(): List<DiagnosticModel> {
        TODO("Not yet implemented")
    }

    override fun getListEvents(idDiagnostic: Int): List<EventModel> {
        TODO("Not yet implemented")
    }

    private fun EventModel.toData(idDiagnostic: Int): SensorEventEntity = SensorEventEntity(
        idEvent = id,
        xValue = x_value,
        yValue = y_value,
        zValue = z_value,
        timestamp = timestamp,
        fkDiagnostic = idDiagnostic
    )

    private fun DiagnosticModel.toData(): DiagnosticsEntity = DiagnosticsEntity(
        idDiagnostic = idDiagnostic,
        dateTime = dateTime,
        fkCarMark = fkCarMark,
        fkCarModel = fkCarModel,
        fkCarGeneration = fkCarGeneration,
        fkTypeFuel = fkTypeFuel,
        engineVolume = engineVolume,
        note = note,
        fkVibrationSource = fkVibrationSource
    )
}