package com.papmobdev.data.mapping

import android.hardware.SensorEvent
import com.papmobdev.data.database.entities.diagnostic.DiagnosticsEntity
import com.papmobdev.data.database.entities.diagnostic.SensorEventEntity
import com.papmobdev.domain.diagnostic.models.DiagnosticModel
import com.papmobdev.domain.sensor.models.EventModel

internal fun SensorEvent.toDomain(): EventModel = EventModel(
    id = null,
    x_value = values[0],
    y_value = values[1],
    z_value = values[2],
    timestamp = timestamp
)

internal fun EventModel.toData(idDiagnostic: Long): SensorEventEntity = SensorEventEntity(
    idEvent = id,
    xValue = x_value,
    yValue = y_value,
    zValue = z_value,
    timestamp = timestamp,
    fkDiagnostic = idDiagnostic
)

internal fun DiagnosticModel.toData(): DiagnosticsEntity = DiagnosticsEntity(
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

internal fun DiagnosticsEntity.toDomain() = DiagnosticModel(
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