package com.papmobdev.enginevibrationanalyzerkotlin.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "measurement_entity", foreignKeys = [
    ForeignKey(
            entity = MeasurementEntity::class,
            childColumns = arrayOf("fk_test"),
            parentColumns = arrayOf("id_measurement"),
            onDelete = ForeignKey.SET_NULL)])

data class MeasurementEntity(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_measurement") var idMeasurement: Int? = 0,

        @ColumnInfo(name = "x_value") var xValue: Double? = 0.0,

        @ColumnInfo(name = "y_value") var yValue: Double? = 0.0,

        @ColumnInfo(name = "z_value") var zValue: Double? = 0.0,

        @ColumnInfo(name = "timestamp") var timestamp: Double? = 0.0,

        @ColumnInfo(name = "fk_test") var fkTest: Int? = 0

)