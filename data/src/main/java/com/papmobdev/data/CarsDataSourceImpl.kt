package com.papmobdev.data

import com.papmobdev.data.entities.CarGenerationEntity
import com.papmobdev.data.entities.CarMarkEntity
import com.papmobdev.data.entities.CarModelEntity
import com.papmobdev.data.entities.LastCarConfigurationEntity
import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.models.CarGeneration
import com.papmobdev.domain.cars.models.CarMark
import com.papmobdev.domain.cars.models.CarModel
import com.papmobdev.domain.cars.models.LastCarConfigurationModel

class CarsDataSourceImpl(private val dao: AppDataBaseDao) : CarsDataSource {
    override suspend fun getMarks(): List<CarMark> =
        dao.getCarMarks().map {
            it.toDomain()
        }

    override suspend fun getModels(idMark: Int): List<CarModel> =
        dao.getCarModels(idMark).map {
            it.toDomain()
        }

    override suspend fun getGenerations(idModel: Int): List<CarGeneration> =
        dao.getCarGenerations(idModel).map {
            it.toDomain()
        }

    override suspend fun getLastCarConfiguration(): LastCarConfigurationModel =
        dao.getLastConfiguration()?.toDomain() ?: LastCarConfigurationModel()

    override suspend fun updateLastCarConfiguration(newConfiguration: LastCarConfigurationModel): Boolean {
        val oldConfiguration = dao.getLastConfiguration() ?: LastCarConfigurationEntity()

        val markIsEqual = oldConfiguration.fkCarMark == newConfiguration.fkCarMark

        val modelIsEqual = oldConfiguration.fkCarModel == newConfiguration.fkCarModel

        val generationIsEqual = oldConfiguration.fkCarGeneration == newConfiguration.fkCarGeneration

        val pushConfiguration = when {
            markIsEqual && modelIsEqual && generationIsEqual -> oldConfiguration
            markIsEqual && modelIsEqual -> LastCarConfigurationEntity(
                idConfiguration = 1,
                fkCarMark = oldConfiguration.fkCarMark,
                fkCarModel = oldConfiguration.fkCarModel,
                fkCarGeneration = newConfiguration.fkCarGeneration
            )
            markIsEqual -> LastCarConfigurationEntity(
                idConfiguration = 1,
                fkCarMark = oldConfiguration.fkCarMark,
                fkCarModel = newConfiguration.fkCarModel,
                fkCarGeneration = null
            )
            else -> LastCarConfigurationEntity(
                idConfiguration = 1,
                fkCarMark = newConfiguration.fkCarMark,
                fkCarModel = null,
                fkCarGeneration = null
            )
        }
        return try {
            dao.updateLastConfiguration(pushConfiguration)
            true
        } catch (e: Exception) {
            false
        }

    }

    private fun LastCarConfigurationEntity.toDomain(): LastCarConfigurationModel {
        var mark: CarMarkEntity?
        fkCarMark.let {
            mark = dao.getOneCarMark(it)
        }
        var model: CarModelEntity?
        fkCarModel.let {
            model = dao.getOneCarModel(it)
        }
        var generation: CarGenerationEntity?
        fkCarGeneration.let {
            generation = dao.getOneCarGeneration(it)
        }
        return LastCarConfigurationModel(
            fkCarMark = mark?.idCarMark,
            nameMark = mark?.carMarkName,
            fkCarModel = model?.idCarModel,
            nameModel = model?.carModelName,
            fkCarGeneration = generation?.idCarGeneration,
            nameGeneration = generation?.carGenerationName
        )
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