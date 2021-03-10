package com.papmobdev.data.database.entities.parametersconfiguration

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_mark")
data class CarMarkEntity(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_car_mark")
        @Nullable val idCarMark: Int,

        @ColumnInfo(name = "name")
        @Nullable val carMarkName: String

)