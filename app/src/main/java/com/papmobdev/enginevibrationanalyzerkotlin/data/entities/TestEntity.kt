package com.papmobdev.enginevibrationanalyzerkotlin.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Test_entity")

data class TestEntity(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_test") var idTest: Int? = 0,

        @ColumnInfo(name = "fk_mark") var fkCarMark: Int? = 0,

        @ColumnInfo(name = "fk_model") var fkCarModel: Int? = 0,

        @ColumnInfo(name = "fk_generation") var fkCarGeneration: Int? = 0,

        @ColumnInfo(name = "car_registration_plate") var registrationNumber: String? = "",

        @ColumnInfo(name = "note") var note: String? = ""
)