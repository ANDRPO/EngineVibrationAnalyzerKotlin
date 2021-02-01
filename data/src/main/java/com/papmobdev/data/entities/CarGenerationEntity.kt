package com.papmobdev.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_generation")
data class CarGenerationEntity(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_car_generation") var idCarGeneration: Int,

        @ColumnInfo(name = "name") var carGenerationName: String,

        @ColumnInfo(name = "fk_car_model") var fkCarModel: Int

)