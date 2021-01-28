package com.papmobdev.enginevibrationanalyzerkotlin.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Car_mark")
data class CarMarkEntity(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_car_mark") var idCarMark: Int? = 0,

        @ColumnInfo(name = "name") var carMarkName: String? = ""

)