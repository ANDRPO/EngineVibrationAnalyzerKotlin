package com.papmobdev.enginevibrationanalyzerkotlin.data

import android.content.Context
import androidx.room.Room

object AppDataBaseInstance {
    fun build(context: Context): AppDataBase = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        "app_database"
    ).createFromAsset("databases/cars.db").build()
}