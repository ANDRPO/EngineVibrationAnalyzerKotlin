package com.papmobdev.domain.cars

import com.papmobdev.domain.cars.models.*
import kotlinx.coroutines.flow.Flow

interface CarsDataSource {
    fun getMarks(): List<CarMark>
    fun getModels(idMark: Int): List<CarModel>
    fun getGenerations(idModel: Int): List<CarGeneration>
    fun getTypesFuel(): List<TypeFuel>

    fun getLastCarConfiguration(): Flow<LastCarConfigurationModel>
    fun updateLastCarConfiguration(lastCarConfigurationModel: LastCarConfigurationModel): Boolean
}