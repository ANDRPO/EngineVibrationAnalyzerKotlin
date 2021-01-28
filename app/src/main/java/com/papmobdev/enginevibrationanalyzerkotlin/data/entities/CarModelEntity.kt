package com.papmobdev.enginevibrationanalyzerkotlin.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Car_model")
data class CarModelEntity(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_car_model") var idCarModel: Int? = 0,

        @ColumnInfo(name = "fk_car_mark") var fkCarMark: Int? = 0,

        @ColumnInfo(name = "name") var carModelName: String? = ""

)