package com.papmobdev.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "diagnostic")
data class DiagnosticsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_diagnostic") val idDiagnostic: Int? = null,

    @ColumnInfo(name = "date_time") val time: Date,

    @ColumnInfo(name = "fk_car_mark") val fkCarMark: Int? = null,

    @ColumnInfo(name = "fk_car_model") val fkCarModel: Int? = null,

    @ColumnInfo(name = "fk_car_generation") val fkCarGeneration: Int? = null,

    @ColumnInfo(name = "fk_type_fuel", defaultValue = "1") val fkTypeFuel: Int?,

    @ColumnInfo(name = "engine_volume") val engineVolume: Double? = null,

    @ColumnInfo(name = "note") val note: String? = null
)
