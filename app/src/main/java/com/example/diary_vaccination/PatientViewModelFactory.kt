package com.example.diary_vaccination

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.diary_vaccination.database.DiaryVaccinationDao

class PatientViewModelFactory (
    private val dao: DiaryVaccinationDao,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PatientViewModel::class.java)){
            return PatientViewModel(dao,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}