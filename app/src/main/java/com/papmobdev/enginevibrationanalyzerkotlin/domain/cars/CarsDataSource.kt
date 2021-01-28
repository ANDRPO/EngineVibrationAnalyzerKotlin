package com.papmobdev.enginevibrationanalyzerkotlin.domain.cars

import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.CarGenerationEntity
import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.CarMarkEntity
import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.CarModelEntity

interface CarsDataSource {
    suspend fun getMarks(): List<CarMarkEntity>
    suspend fun getModels(idMark: Int): List<CarModelEntity>
    suspend fun getGenerations(idModel: Int): List<CarGenerationEntity>
}