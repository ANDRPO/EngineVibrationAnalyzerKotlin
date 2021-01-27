package com.papmobdev.enginevibrationanalyzerkotlin.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.papmobdev.enginevibrationanalyzerkotlin.data.pojo.CarGeneration
import com.papmobdev.enginevibrationanalyzerkotlin.data.pojo.CarMark
import com.papmobdev.enginevibrationanalyzerkotlin.data.pojo.CarModel

@Database(entities = [CarMark::class, CarModel::class, CarGeneration::class], version = 2, exportSchema = true)
abstract class CarsDataBase : RoomDatabase() {
    abstract fun carsDao(): CarsDao
}