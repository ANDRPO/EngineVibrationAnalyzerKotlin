package com.papmobdev.data.database.entities.diagnostic

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "sensor_events",
    foreignKeys = [ForeignKey(
        entity = DiagnosticsEntity::class,
        parentColumns = arrayOf("id_diagnostic"),
        childColumns = arrayOf("fk_diagnostic"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class SensorEventEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_event") val idEvent: Int?,

    @ColumnInfo(name = "x_value") val xValue: Float,

    @ColumnInfo(name = "y_value") val yValue: Float,

    @ColumnInfo(name = "z_value") val zValue: Float,

    @ColumnInfo(name = "timestamp") val timestamp: Long,

    @ColumnInfo(name = "fk_diagnostic") val fkDiagnostic: Int

)
