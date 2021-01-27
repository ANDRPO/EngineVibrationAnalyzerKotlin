package com.papmobdev.enginevibrationanalyzerkotlin.data

import android.content.Context
import androidx.room.Room

object CarsDataBaseInstance {
    fun build(context: Context): CarsDataBase = Room.databaseBuilder(
            context,
            CarsDataBase::class.java,
            "cars_database"
    ).build()
}