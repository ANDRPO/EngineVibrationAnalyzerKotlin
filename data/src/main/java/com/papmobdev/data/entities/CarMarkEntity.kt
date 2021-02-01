package com.papmobdev.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_mark")
data class CarMarkEntity(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_car_mark") var idCarMark: Int,

        @ColumnInfo(name = "name") var carMarkName: String

)