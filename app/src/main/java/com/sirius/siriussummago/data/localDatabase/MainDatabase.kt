package com.sirius.siriussummago.data.localDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sirius.siriussummago.data.localDatabase.entities.SummarySubjectEntity


@Database(
    version = 1,
    entities = [SummarySubjectEntity::class]
)
abstract class MainDatabase : RoomDatabase() {
    abstract val mainDao: MainDao
}