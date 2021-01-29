package com.papmobdev.enginevibrationanalyzerkotlin.data.entities

import androidx.room.*

@Entity(tableName = "test_entity")

data class TestEntity(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_test", defaultValue = "0") var idTest: Int? = 0,

        @ColumnInfo(name = "fk_mark") var fkCarMark: Int?,

        @ColumnInfo(name = "fk_model") var fkCarModel: Int?,

        @ColumnInfo(name = "fk_generation") var fkCarGeneration: Int?,

        @ColumnInfo(name = "car_registration_plate") var registrationNumber: String?,

        @ColumnInfo(name = "date", defaultValue = "CURRENT_DATE") var date: String?,

        @ColumnInfo(name = "time", defaultValue = "CURRENT_TIME") var time: String?,

        @ColumnInfo(name = "note") var note: String?
)