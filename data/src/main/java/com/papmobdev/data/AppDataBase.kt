package com.papmobdev.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.papmobdev.data.entities.*

@Database(
    entities = [CarMarkEntity::class, CarModelEntity::class, CarGenerationEntity::class, LastCarConfigurationEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun carsDao(): AppDataBaseDao
}