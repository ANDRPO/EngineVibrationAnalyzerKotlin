package com.papmobdev.domain.cars

import com.papmobdev.domain.cars.models.CarGeneration
import com.papmobdev.domain.cars.models.CarMark
import com.papmobdev.domain.cars.models.CarModel

interface CarsDataSource {
    suspend fun getMarks(): List<CarMark>
    suspend fun getModels(idMark: Int): List<CarModel>
    suspend fun getGenerations(idModel: Int): List<CarGeneration>
}