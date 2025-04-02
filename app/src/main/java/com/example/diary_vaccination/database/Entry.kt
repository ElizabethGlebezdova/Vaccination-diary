package com.example.diary_vaccination.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry")
data class Entry(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "entry_id")
    var entryId: Long = 0L,

    @ColumnInfo(name = "patient_id")
    var patientId: Long,

    @ColumnInfo(name = "vaccine_id")
    var vaccineId: Long,

    @ColumnInfo(name = "component")
    var component: Long = 0L,

    @ColumnInfo(name = "vaccine_date")
    var vaccineDate: String,

    @ColumnInfo(name = "vaccine_time")
    var vaccineTime: String
)
