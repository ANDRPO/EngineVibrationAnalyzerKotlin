package com.papmobdev.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.papmobdev.data.database.entities.*
import com.papmobdev.data.database.typeconverters.DiagnosticsTypeConverter

@Database(
    entities = [
        CarMarkEntity::class,
        CarModelEntity::class,
        CarGenerationEntity::class,
        LastCarConfigurationEntity::class,
        CarTypeFuel::class,
        DiagnosticsEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    DiagnosticsTypeConverter::class
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun carsDao(): AppDataBaseDao
}