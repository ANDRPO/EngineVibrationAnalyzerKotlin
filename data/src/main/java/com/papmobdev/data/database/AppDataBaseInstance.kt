package com.papmobdev.data.database

import android.content.Context
import androidx.room.Room

object AppDataBaseInstance {

    fun build(context: Context): AppDataBase = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        "appDataBase.db"
    ).createFromAsset("databases/appdatabase.db").build()
}