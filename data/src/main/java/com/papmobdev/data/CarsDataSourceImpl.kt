package com.papmobdev.data

import com.papmobdev.data.entities.*
import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.models.*

class CarsDataSourceImpl(private val dao: AppDataBaseDao) : CarsDataSource {
    override fun getMarks() = dao.getCarMarks().map {
        it.toDomain()
    }

    override fun getModels(idMark: Int) = dao.getCarModels(idMark).map {
        it.toDomain()
    }

    override fun getGenerations(idModel: Int) = dao.getCarGenerations(idModel).map {
        it.toDomain()
    }

    override fun getTypesFuel(): List<TypeFuel> = dao.getTypesFuel().map {
        it.toDomain()

    }

    override fun getLastCarConfiguration(): LastCarConfigurationModel =
        dao.getLastConfiguration()?.toDomain() ?: LastCarConfigurationModel()

    override fun updateLastCarConfiguration(newConfiguration: LastCarConfigurationModel): Boolean {
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
        val mark: CarMarkEntity? = dao.getOneCarMark(fkCarMark)
        val model: CarModelEntity? = dao.getOneCarModel(fkCarModel)
        val generation: CarGenerationEntity? = dao.getOneCarGeneration(fkCarGeneration)
        return LastCarConfigurationModel(
            fkCarMark = mark?.idCarMark.takeIf { mark != null },
            nameMark = mark?.carMarkName.takeIf { mark != null },
            fkCarModel = model?.idCarModel.takeIf { model != null },
            nameModel = model?.carModelName.takeIf { model != null },
            fkCarGeneration = generation?.idCarGeneration.takeIf { generation != null },
            nameGeneration = generation?.carGenerationName.takeIf { generation != null }
        )
    }


    private fun CarMarkEntity.toDomain() = CarMark(
        id = this.idCarMark,
        name = this.carMarkName
    )

    private fun CarModelEntity.toDomain() = CarModel(
        id = this.idCarModel,
        name = this.carModelName
    )

    private fun CarGenerationEntity.toDomain() = CarGeneration(
        id = this.idCarGeneration,
        name = this.carGenerationName
    )

    private fun CarTypeFuel.toDomain() = TypeFuel(
        idFuel = this.idFuel,
        nameFuel = this.nameFuel
    )
}


