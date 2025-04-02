package com.example.diary_vaccination

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import com.example.diary_vaccination.database.DiaryVaccinationDao
import com.example.diary_vaccination.database.Vaccine
import kotlinx.coroutines.*

class VaccineViewModel(
    val dao: DiaryVaccinationDao,
    application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val vaccines = dao.getAllVaccines()
    val listVaccinesDB = Transformations.map(vaccines) { vaccines ->
        parselistVaccinesDB(vaccines, application.resources)
    }

    fun initNewVaccine(vaccineName: String, vaccineComponent: Boolean){
        uiScope.launch {
            val newVaccine = Vaccine(vaccineName = vaccineName,
                vaccineComponent = vaccineComponent)
            insertVaccine(newVaccine)
        }
    }

    private suspend fun insertVaccine(newVaccine: Vaccine) {
        withContext(Dispatchers.IO){
            dao.insertVaccine(newVaccine)
        }
    }

    fun clearAllVaccine() {
        uiScope.launch {
            clearAllVaccineQueries()
        }
    }
    private suspend fun clearAllVaccineQueries() {
        withContext(Dispatchers.IO) {
            dao.clearVaccines()
        }
    }

    fun clearToVaccine(id: String) {
        uiScope.launch {
            clearToVaccineQueries(id)
        }
    }
    private suspend fun clearToVaccineQueries(id: String) {
        withContext(Dispatchers.IO) {
            dao.clearToVaccines(id)
        }
    }

}