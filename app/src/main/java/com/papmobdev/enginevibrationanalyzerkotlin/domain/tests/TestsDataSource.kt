package com.papmobdev.enginevibrationanalyzerkotlin.domain.tests

import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.MeasurementEntity
import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.TestEntity

interface TestsDataSource {
    suspend fun getTests(): List<TestEntity>
    suspend fun getMeasurement(idTest: Int): List<MeasurementEntity>
}