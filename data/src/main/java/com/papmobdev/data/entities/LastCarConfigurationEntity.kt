package com.papmobdev.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "last_car_configuration")
data class LastCarConfigurationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_configuration") val idConfiguration: Int?,

    @ColumnInfo(name = "fk_car_mark") val fkCarMark: Int?,

    @ColumnInfo(name = "fk_car_model") val fkCarModel: Int?,

    @ColumnInfo(name = "fk_car_generation") val fkCarGeneration: Int?
)
