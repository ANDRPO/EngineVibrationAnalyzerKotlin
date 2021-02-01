package com.papmobdev.data

import com.papmobdev.data.entities.CarGenerationEntity
import com.papmobdev.data.entities.CarMarkEntity
import com.papmobdev.data.entities.CarModelEntity
import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.models.CarGeneration
import com.papmobdev.domain.cars.models.CarMark
import com.papmobdev.domain.cars.models.CarModel

class CarsDataSourceImpl(private val dao: AppDataBaseDao) : CarsDataSource {
    override suspend fun getMarks(): List<CarMark> =
        dao.getCarMark().map {
            it.toDomain()
        }

    override suspend fun getModels(idMark: Int): List<CarModel> =
        dao.getCarModel(idMark).map {
            it.toDomain()
        }

    override suspend fun getGenerations(idModel: Int): List<CarGeneration> =
        dao.getCarGeneration(idModel).map {
            it.toDomain()
        }

    private fun CarMarkEntity.toDomain(): CarMark = CarMark(
        id = this.idCarMark,
        name = this.carMarkName
    )

    private fun CarModelEntity.toDomain(): CarModel = CarModel(
        id = this.idCarModel,
        name = this.carModelName
    )

    private fun CarGenerationEntity.toDomain(): CarGeneration = CarGeneration(
        id = this.idCarGeneration,
        name = this.carGenerationName
    )
}