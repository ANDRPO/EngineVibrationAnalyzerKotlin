package com.papmobdev.data.database.entities.options

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_vibration_source")
data class CarVibrationSource(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_source") val idSource: Int,

    @ColumnInfo(name = "name_source") val nameSource: String
)