package com.papmobdev.enginevibrationanalyzerkotlin.data

import androidx.room.*
import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDataBaseDao {

    @Insert
    fun insertTest(test: TestEntity)

    @Insert
    fun insertMeasurement(measurement: MeasurementEntity)

    @Delete
    fun deleteTests(vararg tests: TestEntity)

    @Delete
    fun deleteMeasurement(vararg measurements: MeasurementEntity)

    @Query("SELECT * FROM car_mark")
    fun getCarMark(): Flow<List<CarMarkEntity>>

    @Query("SELECT * FROM car_model WHERE fk_car_mark = :idCarMark")
    fun getCarModel(idCarMark: Int): Flow<List<CarModelEntity>>

    @Query("SELECT * FROM car_generation WHERE fk_car_model = :idCarModel")
    fun getCarGeneration(idCarModel: Int): Flow<List<CarGenerationEntity>>

    @Query("""
        SELECT 
            test_entity.id_test as 'id_test', 
            test_entity.car_registration_plate as 'car_registration_plate',
            car_mark.name as 'mark_name',
            car_model.name as 'model_name',
            car_generation.name as 'generation_name',
            date,
            time
            FROM test_entity
            LEFT JOIN car_mark 
            ON car_mark.id_car_mark = fk_mark
            LEFT JOIN car_model 
            ON car_model.id_car_model = fk_model
            LEFT JOIN car_generation 
            ON car_generation.id_car_generation = fk_generation""")

    fun getTests(): Flow<List<TestEntityJoinedCar>>

    @Query("SELECT * FROM measurement_entity WHERE fk_test = :idMeasurement")
    fun getMeasurements(idMeasurement: Int): Flow<List<MeasurementEntity>>


}