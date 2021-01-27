package com.papmobdev.enginevibrationanalyzerkotlin.data.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CAR_MODEL")
data class CarModel(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_car_model") var IDCarModel: Int? = 0,

        @ColumnInfo(name = "id_car_mark") var IDCarMark: Int? = 0,

        @ColumnInfo(name = "name") var CarModelName: String? = null,

        @ColumnInfo(name = "id_car_type") var IDCarType: Int? = 0
)