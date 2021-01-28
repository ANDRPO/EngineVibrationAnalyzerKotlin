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


    fun deleteTests(vararg tests: TestEntity)

    @Delete
    fun deleteMeasurement(vararg measurements: MeasurementEntity)

    @Query("SELECT * FROM Car_mark")
    fun getCarMark(): Flow<List<CarMarkEntity>>

    @Query("SELECT * FROM Car_model WHERE fk_car_mark = :idCarMark")
    fun getCarModel(idCarMark: Int): Flow<List<CarModelEntity>>

    @Query("SELECT * FROM Car_generation WHERE fk_car_model = :idCarModel")
    fun getCarGeneration(idCarModel: Int): Flow<List<CarGenerationEntity>>

    @Query("""
        SELECT 
            Test_entity.id_test as 'id_test', 
            Test_entity.car_registration_plate as 'car_registration_plate',
            Car_mark.name as 'mark_name',
            Car_model.name as 'model_name',
            Car_generation.name as 'generation_name'
            FROM Test_entity
            LEFT JOIN Car_mark 
            ON Car_mark.id_car_mark = fk_mark
            LEFT JOIN Car_model 
            ON Car_model.id_car_model = fk_model
            LEFT JOIN Car_generation 
            ON Car_generation.id_car_generation = fk_generation""")

    fun getTests(): Flow<List<TestEntityJoinedCar>>

    @Query("SELECT * FROM Measurement_entity WHERE fk_test = :idMeasurement")
    fun getMeasurements(idMeasurement: Int): Flow<List<MeasurementEntity>>


}