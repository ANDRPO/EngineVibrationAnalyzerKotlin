package com.papmobdev.enginevibrationanalyzerkotlin.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.basecarentity.BaseCarEntity

@Entity(tableName = "car_mark")
data class CarMarkEntity(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_car_mark") var idCarMark: Int,

        @ColumnInfo(name = "name") var carMarkName: String

): BaseCarEntity {
        override val id: Int
                get() = idCarMark
        override val name: String
                get() = carMarkName
}