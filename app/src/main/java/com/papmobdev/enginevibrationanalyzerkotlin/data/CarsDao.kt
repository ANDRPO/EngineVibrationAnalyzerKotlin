package com.papmobdev.enginevibrationanalyzerkotlin.data

import androidx.room.Dao
import androidx.room.Query
import com.papmobdev.enginevibrationanalyzerkotlin.data.pojo.CarGeneration
import com.papmobdev.enginevibrationanalyzerkotlin.data.pojo.CarMark
import com.papmobdev.enginevibrationanalyzerkotlin.data.pojo.CarModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CarsDao {
    @Query("SELECT * FROM CAR_MARK")
    fun getCarMark(): Flow<List<CarMark>>

    @Query("SELECT * FROM CAR_MODEL WHERE id_car_mark = :IDCarMark")
    fun getCarModel(IDCarMark: Int): Flow<List<CarModel>>

    @Query("SELECT * FROM CAR_GENERATION WHERE id_car_model = :IDCarModel")
    fun getCarGeneration(IDCarModel: Int): Flow<List<CarGeneration>>
}