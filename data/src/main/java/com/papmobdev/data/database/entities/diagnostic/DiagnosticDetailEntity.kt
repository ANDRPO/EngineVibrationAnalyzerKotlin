package com.papmobdev.data.database.entities.diagnostic

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import java.util.*

@DatabaseView(
    """
    SELECT
    diagnostic.id_diagnostic,
    diagnostic.date_time,
    diagnostic.fk_car_mark,
    diagnostic.fk_car_model,
    diagnostic.fk_car_generation,
    diagnostic.fk_type_fuel,
    diagnostic.engine_volume,
    diagnostic.engine_volume,
    diagnostic.note,
    diagnostic.fk_vibration_source,
    car_mark.name as 'mark_name',
    car_model.name as 'model_name',
    car_generation.name as 'generation_name',
    car_vibration_source.name_source as 'vibration_source_name',
    types_fuel.name as 'type_fuel_name'
    FROM diagnostic
    INNER JOIN car_mark
    ON car_mark.id_car_mark = diagnostic.fk_car_mark
    INNER JOIN car_model
    ON car_model.id_car_model = diagnostic.fk_car_model
    INNER JOIN car_generation
    ON car_generation.id_car_generation = diagnostic.fk_car_generation
    INNER JOIN car_vibration_source
    ON car_vibration_source.id_source = diagnostic.fk_vibration_source
    INNER JOIN types_fuel
    ON types_fuel.id_type_fuel = diagnostic.fk_type_fuel
"""
)
data class DiagnosticDetailEntity(
    @ColumnInfo(name = "id_diagnostic") val idDiagnostic: Int? = null,

    @ColumnInfo(name = "date_time") val time: Date,

    @ColumnInfo(name = "fk_car_mark") val fkCarMark: Int? = null,

    @ColumnInfo(name = "fk_car_model") val fkCarModel: Int? = null,

    @ColumnInfo(name = "fk_car_generation") val fkCarGeneration: Int? = null,

    @ColumnInfo(name = "fk_type_fuel", defaultValue = "1") val fkTypeFuel: Int?,

    @ColumnInfo(name = "engine_volume") val engineVolume: Double? = null,

    @ColumnInfo(name = "note") val note: String? = null,

    @ColumnInfo(name = "fk_vibration_source") val fkVibrationSource: Int? = null,

    @ColumnInfo(name = "mark_name") val markName: String? = null,

    @ColumnInfo(name = "model_name") val modelName: String? = null,

    @ColumnInfo(name = "generation_name") val generationName: String? = null,

    @ColumnInfo(name = "vibration_source_name") val vibrationSourceName: String,

    @ColumnInfo(name = "type_fuel_name") val typeFuelName: String

)
