package com.papmobdev.data.database.entities.options

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
        tableName = "car_model",
        foreignKeys = [ForeignKey(
                entity = CarMarkEntity::class,
                parentColumns = ["id_car_mark"],
                childColumns = ["fk_car_mark"],
                onDelete = ForeignKey.CASCADE
        )]
)
data class CarModelEntity(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_car_model") val idCarModel: Int,

        @ColumnInfo(name = "fk_car_mark") val fkCarMark: Int,

        @ColumnInfo(name = "name") val carModelName: String

)