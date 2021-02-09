package com.papmobdev.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object AppDataBaseInstance {

    fun build(context: Context): AppDataBase = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        "appDataBase.db"
    ).createFromAsset("databases/appdatabase.db").build()
}