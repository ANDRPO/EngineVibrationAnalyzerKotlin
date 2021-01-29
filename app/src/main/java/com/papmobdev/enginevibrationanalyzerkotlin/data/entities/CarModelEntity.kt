package com.papmobdev.enginevibrationanalyzerkotlin.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.basecarentity.BaseCarEntity

@Entity(tableName = "car_model")
data class CarModelEntity(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_car_model") var idCarModel: Int,

        @ColumnInfo(name = "fk_car_mark") var fkCarMark: Int,

        @ColumnInfo(name = "name") var carModelName: String

) : BaseCarEntity {
        override val id: Int
                get() = idCarModel
        override val name: String
                get() = carModelName
}