package com.papmobdev.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "types_fuel")
data class CarTypeFuel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_type_fuel") val idFuel: Int,

    @ColumnInfo(name = "name") val nameFuel: String
)
