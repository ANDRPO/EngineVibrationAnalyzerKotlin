package com.papmobdev.domain.tests

import com.papmobdev.domain.tests.models.MeasurementModel
import com.papmobdev.domain.tests.models.TestModel

interface TestsDataSource {
    suspend fun getTests(): List<TestModel>
    suspend fun getMeasurement(idTest: Int): List<MeasurementModel>
}