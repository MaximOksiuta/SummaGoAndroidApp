package com.sirius.siriussummago.data.localDatabase

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    version = 1,
    entities = []
)
abstract class MainDatabase : RoomDatabase() {
    abstract val mainDao: MainDao
}