package com.papmobdev.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.papmobdev.data.database.entities.diagnostic.DiagnosticsEntity
import com.papmobdev.data.database.entities.diagnostic.LastCarConfigurationEntity
import com.papmobdev.data.database.entities.diagnostic.SensorEventEntity
import com.papmobdev.data.database.entities.options.*
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

    @Insert
    fun insertSensorEvents(listEvents: List<SensorEventEntity>)

    @Query("SELECT * FROM diagnostic ORDER BY id_diagnostic DESC LIMIT 1")
    fun getLastDiagnostic(): DiagnosticsEntity
}