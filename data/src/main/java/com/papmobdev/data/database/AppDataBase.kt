package com.papmobdev.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.papmobdev.data.database.entities.diagnostic.DiagnosticDetailEntity
import com.papmobdev.data.database.entities.diagnostic.DiagnosticsEntity
import com.papmobdev.data.database.entities.diagnostic.LastCarConfigurationEntity
import com.papmobdev.data.database.entities.options.CarGenerationEntity
import com.papmobdev.data.database.entities.options.CarMarkEntity
import com.papmobdev.data.database.entities.options.CarModelEntity
import com.papmobdev.data.database.entities.options.CarTypeFuel
import com.papmobdev.data.database.typeconverters.DiagnosticsTypeConverter

@Database(
    entities = [
        CarMarkEntity::class,
        CarModelEntity::class,
        CarGenerationEntity::class,
        LastCarConfigurationEntity::class,
        CarTypeFuel::class,
        DiagnosticsEntity::class],
    views = [DiagnosticDetailEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    DiagnosticsTypeConverter::class
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun carsDao(): AppDataBaseDao
}