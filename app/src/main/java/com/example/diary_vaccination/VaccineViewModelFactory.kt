package com.example.diary_vaccination

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.diary_vaccination.database.DiaryVaccinationDao

class VaccineViewModelFactory (
    private val dao: DiaryVaccinationDao,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VaccineViewModel::class.java)){
            return VaccineViewModel(dao,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}