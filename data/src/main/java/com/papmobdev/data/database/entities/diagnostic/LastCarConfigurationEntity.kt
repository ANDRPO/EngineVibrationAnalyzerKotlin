package com.papmobdev.data.database.entities.diagnostic

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.papmobdev.data.database.entities.parametersconfiguration.*

@Entity(
    tableName = "last_car_configuration",
    foreignKeys = [ForeignKey(
        entity = CarMarkEntity::class,
        parentColumns = arrayOf("id_car_mark"),
        childColumns = arrayOf("fk_car_mark"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = CarModelEntity::class,
        parentColumns = arrayOf("id_car_model"),
        childColumns = arrayOf("fk_car_model"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = CarGenerationEntity::class,
        parentColumns = arrayOf("id_car_generation"),
        childColumns = arrayOf("fk_car_generation"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = CarTypeFuel::class,
        parentColumns = arrayOf("id_type_fuel"),
        childColumns = arrayOf("fk_type_fuel"),
        onDelete = ForeignKey.CASCADE
    ),ForeignKey(
        entity = CarVibrationSource::class,
        parentColumns = arrayOf("id_source"),
        childColumns = arrayOf("fk_vibration_source"),
        onDelete = ForeignKey.CASCADE
    )]
)

data class LastCarConfigurationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_configuration") val idConfiguration: Int? = null,

    @ColumnInfo(name = "fk_car_mark") val fkCarMark: Int? = null,

    @ColumnInfo(name = "fk_car_model") val fkCarModel: Int? = null,

    @ColumnInfo(name = "fk_car_generation") val fkCarGeneration: Int? = null,

    @ColumnInfo(name = "fk_type_fuel", defaultValue = "1") val fkTypeFuel: Int? = null,

    @ColumnInfo(name = "engine_volume") val engineVolume: Double? = null,

    @ColumnInfo(name = "note") val note: String? = null,

    @ColumnInfo(name = "fk_vibration_source") val fkVibrationSource: Int? = null
)
