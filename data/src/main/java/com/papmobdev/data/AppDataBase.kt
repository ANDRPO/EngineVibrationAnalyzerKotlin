package com.papmobdev.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.papmobdev.data.entities.*

@Database(
    entities = [CarMarkEntity::class, CarModelEntity::class, CarGenerationEntity::class, MeasurementEntity::class, TestEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun carsDao(): AppDataBaseDao
}