package com.papmobdev.data

import androidx.room.*
import com.papmobdev.data.entities.*

@Dao
interface AppDataBaseDao {

    @Query("SELECT * FROM car_mark")
    fun getCarMarks(): List<CarMarkEntity>

    @Query("SELECT * FROM car_model WHERE fk_car_mark = :idCarMark")
    fun getCarModels(idCarMark: Int): List<CarModelEntity>

    @Query("SELECT * FROM car_generation WHERE fk_car_model = :idCarModel")
    fun getCarGenerations(idCarModel: Int): List<CarGenerationEntity>

    @Query("SELECT * FROM last_car_configuration")
    fun getLastConfiguration(): LastCarConfigurationEntity?

    @Query("SELECT * FROM car_mark WHERE id_car_mark = :idCarMark")
    fun getOneCarMark(idCarMark: Int?): CarMarkEntity?

    @Query("SELECT * FROM car_model WHERE id_car_model = :idCarModel")
    fun getOneCarModel(idCarModel: Int?): CarModelEntity?

    @Query("SELECT * FROM car_generation WHERE id_car_generation = :idCarGeneration")
    fun getOneCarGeneration(idCarGeneration: Int?): CarGenerationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateLastConfiguration(lastCarConfigurationEntity: LastCarConfigurationEntity)

}