package com.papmobdev.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object AppDataBaseInstance {

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """CREATE TABLE "last_car_configuration" (
        	    "id_configuration"	INTEGER,
	            "fk_car_mark"	INTEGER,
	            "fk_car_model"	INTEGER,
	            "fk_car_generation"	INTEGER,
            	PRIMARY KEY("id_configuration")
                )"""
            )
        }
    }

    private val container = arrayOf(MIGRATION_1_2)

    fun build(context: Context): AppDataBase = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        "appDatBase.db"
    ).createFromAsset("databases/cars.db").build()
}