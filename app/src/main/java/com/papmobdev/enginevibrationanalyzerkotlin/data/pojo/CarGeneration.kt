package com.papmobdev.enginevibrationanalyzerkotlin.data.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CAR_GENERATION")
data class CarGeneration(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_car_generation") var IDCarGeneration: Int? = 0,

        @ColumnInfo(name = "name") var CarGenerationName: String? = null,

        @ColumnInfo(name = "id_car_model") var IDCarModel: Int? = 0,

        @ColumnInfo(name = "year_begin") var year_begin: String? = null,

        @ColumnInfo(name = "year_end") var year_end: String? = null,

        @ColumnInfo(name = "id_car_type") var IDCarType: Int? = 0
)