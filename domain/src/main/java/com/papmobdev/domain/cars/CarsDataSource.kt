package com.papmobdev.domain.cars

import com.papmobdev.domain.cars.models.*

interface CarsDataSource {
    fun getMarks(): List<CarMark>
    fun getModels(idMark: Int): List<CarModel>
    fun getGenerations(idModel: Int): List<CarGeneration>
    fun getTypesFuel(): List<TypeFuel>

    fun getLastCarConfiguration(): LastCarConfigurationModel
    fun updateLastCarConfiguration(lastCarConfigurationModel: LastCarConfigurationModel): Boolean
}