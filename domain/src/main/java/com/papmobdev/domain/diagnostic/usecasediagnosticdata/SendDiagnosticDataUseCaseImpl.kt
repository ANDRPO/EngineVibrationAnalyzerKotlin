package com.papmobdev.domain.diagnostic.usecasediagnosticdata

import com.papmobdev.domain.cars.CarsDataSource
import com.papmobdev.domain.cars.models.CarConfiguration
import com.papmobdev.domain.diagnostic.DiagnosticDataSource
import com.papmobdev.domain.diagnostic.models.DiagnosticModel
import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.util.*

@ExperimentalCoroutinesApi
class SendDiagnosticDataUseCaseImpl(
    private val diagnosticDataSource: DiagnosticDataSource,
    private val carsDataSource: CarsDataSource
) : SendDiagnosticDataUseCase {
    override fun writeDiagnostic(listEvents: List<EventModel>): Flow<Result<Boolean>> = flow {
        val diagnosticModel = getCarConfiguration().firstOrNull()?.let {
            return@let it.toDiagnosticModel()
        } ?: run {
            emit(Result.success(false))
            return@flow
        }

        try {
            diagnosticDataSource.sendDiagnostic(diagnosticModel)
            val idLastDiagnostic = diagnosticDataSource.getLastDiagnostic().idDiagnostic
            idLastDiagnostic?.let {
                diagnosticDataSource.sendEvents(it, listEvents)
                emit(Result.success(true))
            } ?: run {
                emit(Result.success(false))
                return@flow
            }
        } catch (e: Exception) {
            emit(Result.success(false))
            return@flow
        }
    }.flowOn(Dispatchers.IO)

    private fun getCarConfiguration() = carsDataSource.getLastCarConfiguration()

    private fun CarConfiguration.toDiagnosticModel() = DiagnosticModel(
        idDiagnostic = null,
        dateTime = Date(),
        fkCarMark = fkCarMark,
        fkCarModel = fkCarModel,
        fkCarGeneration = fkCarGeneration,
        fkTypeFuel = fkTypeFuel,
        engineVolume = engineVolume,
        note = note,
        fkVibrationSource = fkTypeSource
    )
}