package com.sirius.siriussummago.data.localDatabase.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SummarySubjectEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val name: String
)
