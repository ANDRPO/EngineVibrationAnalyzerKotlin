package com.papmobdev.enginevibrationanalyzerkotlin.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TestEntityJoinedCar(

        @PrimaryKey
        @ColumnInfo(name = "id_test") var idTest: Int? = 0,

        @ColumnInfo(name = "car_registration_plate") var carRegistrationPlate: String? = "",

        @ColumnInfo(name = "mark_name") var markName: String? = "",

        @ColumnInfo(name = "model_name") var modelName: String? = "",

        @ColumnInfo(name = "generation_name") var generationName: String? = ""

)