package com.papmobdev.data.database

import androidx.room.*
import com.papmobdev.data.database.entities.diagnostic.DiagnosticsEntity
import com.papmobdev.data.database.entities.diagnostic.LastCarConfigurationEntity
import com.papmobdev.data.database.entities.diagnostic.SensorEventEntity
import com.papmobdev.data.database.entities.options.*
import com.papmobdev.domain.sensor.models.EventModel
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDataBaseDao {

    @Query("SELECT * FROM car_mark ORDER BY name ASC")
    fun getCarMarks(): List<CarMarkEntity>

    @Query("SELECT * FROM car_model WHERE fk_car_mark = :idCarMark ORDER BY name ASC")
    fun getCarModels(idCarMark: Int): List<CarModelEntity>

    @Query("SELECT * FROM car_generation WHERE fk_car_model = :idCarModel ORDER BY name ASC")
    fun getCarGenerations(idCarModel: Int): List<CarGenerationEntity>

    @Query("SELECT * FROM car_vibration_source")
    fun getTypesVibrationSource(): List<CarVibrationSource>

    @Query("SELECT * FROM types_fuel")
    fun getTypesFuel(): List<CarTypeFuel>

    @Query("SELECT * FROM last_car_configuration")
    fun getLastConfiguration(): Flow<LastCarConfigurationEntity?>

    @Query("SELECT * FROM car_mark WHERE id_car_mark = :idCarMark")
    fun getOneCarMark(idCarMark: Int?): CarMarkEntity?

    @Query("SELECT * FROM car_model WHERE id_car_model = :idCarModel")
    fun getOneCarModel(idCarModel: Int?): CarModelEntity?

    @Query("SELECT * FROM car_generation WHERE id_car_generation = :idCarGeneration")
    fun getOneCarGeneration(idCarGeneration: Int?): CarGenerationEntity?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateLastConfiguration(lastCarConfigurationEntity: LastCarConfigurationEntity)


    @Insert
    fun insertDiagnostic(diagnosticsEntity: DiagnosticsEntity)

    @Transaction
    fun insertDiagnosticEventsTransaction(
        diagnosticsEntity: DiagnosticsEntity,
        listEvents: List<EventModel>
    ) {
        insertDiagnostic(diagnosticsEntity)
        val idDiagnostic: Int = getLastDiagnostic().idDiagnostic.let { return@let it } ?: throw Exception()
        insertSensorEvents(listEvents.toData(idDiagnostic))
    }

    @Insert
    fun insertSensorEvents(listEvents: List<SensorEventEntity>)

    @Query("SELECT * FROM diagnostic ORDER BY id_diagnostic DESC LIMIT 1")
    fun getLastDiagnostic(): DiagnosticsEntity


    private fun List<EventModel>.toData(idDiagnostic: Int) = map {
        it.toData(idDiagnostic)
    }

    private fun EventModel.toData(idDiagnostic: Int): SensorEventEntity = SensorEventEntity(
        idEvent = id,
        xValue = x_value,
        yValue = y_value,
        zValue = z_value,
        timestamp = timestamp,
        fkDiagnostic = idDiagnostic
    )
}