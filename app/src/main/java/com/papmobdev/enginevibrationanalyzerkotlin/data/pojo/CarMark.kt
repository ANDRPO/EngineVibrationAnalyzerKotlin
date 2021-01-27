package com.papmobdev.enginevibrationanalyzerkotlin.data.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CAR_MARK")
data class CarMark(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_car_mark") var IDCarMark: Int? = 0,

        @ColumnInfo(name = "name") var CarMarkName: String? = null,

        @ColumnInfo(name = "id_car_type") var IDCarType: Int? = 0
)