package com.example.diary_vaccination.database

data class EntryAll(
    val entry_id: Long,
    val patient_name: String,
    val patient_last_name: String,
    val patient_birthday: String,
    val vaccine_name: String,
    val component: Long,
    val vaccine_date: String,
    val vaccine_time: String
)
