package com.papmobdev.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.papmobdev.data.database.entities.*

@Database(
    entities = [
        CarMarkEntity::class,
        CarModelEntity::class,
        CarGenerationEntity::class,
        LastCarConfigurationEntity::class,
        CarTypeFuel::class],
    version = 1,
    exportSchema = true
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun carsDao(): AppDataBaseDao
}