package com.papmobdev.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_model")
data class CarModelEntity(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_car_model") var idCarModel: Int,

        @ColumnInfo(name = "fk_car_mark") var fkCarMark: Int,

        @ColumnInfo(name = "name") var carModelName: String

)