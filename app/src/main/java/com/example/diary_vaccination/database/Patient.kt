package com.example.diary_vaccination.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patient")
data class Patient(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "patient_id")
    var patientId: Long = 0L,

    @ColumnInfo(name = "patient_name")
    var patientName: String = "",

    @ColumnInfo(name = "patient_last_name")
    var patientLastName: String = "",

    @ColumnInfo(name = "patient_surname")
    var patientSurname: String = "",

    @ColumnInfo(name = "patient_birthday")
    var patientBirthday: String
)
