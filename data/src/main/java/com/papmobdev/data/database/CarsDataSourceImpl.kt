package com.papmobdev.data.database

import com.papmobdev.data.database.entities.diagnostic.LastCarConfigurationEntity
import com.papmobdev.data.database.entities.options.CarGenerationEntity
import com.papmobdev.data.database.entities.options.CarMarkEntity
import com.papmobdev.data.database.entities.options.CarModelEntity
import com.papmobdev.data.database.entities.options.CarTypeFuel
import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

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

    override fun getLastCarConfiguration(): Flow<CarConfigurationModel> =
        dao.getLastConfiguration().map {
            it.toDomain()
        }.flowOn(Dispatchers.IO)


    override fun updateLastCarConfiguration(newConfiguration: CarConfigurationModel): Boolean {
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

    private fun LastCarConfigurationEntity?.toDomain(): CarConfigurationModel =
        if (this == null) {
            CarConfigurationModel()
        } else {
            CarConfigurationModel(
                fkCarMark = fkCarMark,
                nameMark = fkCarMark.let { dao.getOneCarMark(fkCarMark)?.carMarkName },
                fkCarModel = fkCarModel,
                nameModel = fkCarModel.let { dao.getOneCarModel(fkCarModel)?.carModelName },
                fkCarGeneration = fkCarGeneration,
                nameGeneration = fkCarGeneration.let { dao.getOneCarGeneration(fkCarGeneration)?.carGenerationName },
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


