package com.example.diary_vaccination

interface PatientOnClickListener {
    fun patientOnClick(id: String)

    fun navigatePatientEdit(id: String)
}