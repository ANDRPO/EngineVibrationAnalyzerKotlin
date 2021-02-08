package com.papmobdev.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_generation")
data class CarGenerationEntity(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_car_generation") val idCarGeneration: Int?,

        @ColumnInfo(name = "name") val carGenerationName: String?,

        @ColumnInfo(name = "fk_car_model") val fkCarModel: Int?

)