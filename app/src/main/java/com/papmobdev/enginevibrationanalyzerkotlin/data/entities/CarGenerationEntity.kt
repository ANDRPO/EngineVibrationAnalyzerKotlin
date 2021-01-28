package com.papmobdev.enginevibrationanalyzerkotlin.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.basecarentity.BaseCarEntity

@Entity(tableName = "Car_generation")
data class CarGenerationEntity(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_car_generation") var idCarGeneration: Int? = 0,

        @ColumnInfo(name = "name") var carGenerationName: String? = "",

        @ColumnInfo(name = "fk_car_model") var fkCarModel: Int? = 0

) : BaseCarEntity() {

    override var id: Int?
        get() = this.idCarGeneration
        set(value) {}

    override var name: String?
        get() = this.carGenerationName
        set(value) {}

}