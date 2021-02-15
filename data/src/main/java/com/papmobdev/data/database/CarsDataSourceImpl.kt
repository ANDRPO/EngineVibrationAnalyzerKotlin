package com.papmobdev.data.database

import com.papmobdev.data.database.entities.*
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
        val pushConfiguration = LastCarConfigurationEntity(
            idConfiguration = 1,
            fkCarMark = newConfiguration.fkCarMark,
            fkCarModel = newConfiguration.fkCarModel,
            fkCarGeneration = newConfiguration.fkCarGeneration,
            fkTypeFuel = newConfiguration.fkTypeFuel,
            engineVolume = newConfiguration.engineVolume,
            note = newConfiguration.note
        )
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
            fkCarMark = mark?.idCarMark,
            nameMark = mark?.carMarkName,
            fkCarModel = model?.idCarModel,
            nameModel = model?.carModelName,
            fkCarGeneration = generation?.idCarGeneration,
            nameGeneration = generation?.carGenerationName,
            fkTypeFuel = fkTypeFuel,
            engineVolume = engineVolume,
            note = note
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


