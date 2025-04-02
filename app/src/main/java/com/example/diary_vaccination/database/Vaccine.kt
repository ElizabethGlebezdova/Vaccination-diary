package com.example.diary_vaccination.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vaccine")
data class Vaccine(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "vaccine_id")
    var vaccineId: Long = 0L,

    @ColumnInfo(name = "vaccine_name")
    var vaccineName: String = "",

    @ColumnInfo(name = "vaccine_component")
    var vaccineComponent: Boolean = false
)
