package com.papmobdev.data

import android.content.Context
import androidx.room.Room

object AppDataBaseInstance {
    fun build(context: Context): AppDataBase = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        "cars.db"
    ).createFromAsset("databases/cars.db").build()
}