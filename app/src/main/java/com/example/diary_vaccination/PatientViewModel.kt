package com.example.diary_vaccination

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.diary_vaccination.database.DiaryVaccinationDao
import com.example.diary_vaccination.database.Patient
import kotlinx.coroutines.*

class PatientViewModel(
    val dao: DiaryVaccinationDao,
    application: Application) : AndroidViewModel(application) {
    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val patients = dao.getAllPatient()
    val listPatientsDB = Transformations.map(patients) { users ->
        parseListPatientsDB(users, application.resources)
    }
    fun initNewPatient(patientName: String, patientLastName: String,
                       patientSurname: String, patientBirthday: String){
        uiScope.launch {
            val newPatient = Patient(patientName = patientName, patientLastName = patientLastName,
                patientSurname = patientSurname, patientBirthday = patientBirthday)
            insertPatient(newPatient)
        }
    }

    private suspend fun insertPatient(newPatient: Patient) {
        withContext(Dispatchers.IO){
            dao.insertPatient(newPatient)
        }
    }

    fun getPatientById(patientId: String): LiveData<Patient?> {
        val patientData = MutableLiveData<Patient?>()
        uiScope.launch {
            val patient = getPatientByIdFromDb(patientId)
            patientData.postValue(patient)
        }
        return patientData
    }

    private suspend fun getPatientByIdFromDb(patientId: String): Patient? {
        return withContext(Dispatchers.IO) {
            dao.getPatient(patientId.toLong())
        }
    }


    fun updatePatient(patientId: String, patientName: String, patientLastName: String,
                      patientSurname: String, patientBirthday: String){
        uiScope.launch {
            updatePatientQuery(patientId, patientName, patientLastName, patientSurname, patientBirthday)
        }
    }

    private suspend fun updatePatientQuery(
        patientId: String,
        patientName: String,
        patientLastName: String,
        patientSurname: String,
        patientBirthday: String
    ) {
        withContext(Dispatchers.IO){
            dao.updatePatient(patientId, patientName, patientLastName, patientSurname, patientBirthday)
        }
    }

    fun clearAllPatients(){
        uiScope.launch {
            clearAllPatientsQueries()
        }
    }

    private suspend fun clearAllPatientsQueries() {
        withContext(Dispatchers.IO){
            dao.clearPatient()
        }
    }

    fun clearToPatient(id: String){
        uiScope.launch{
            clearToPatientQueries(id)
        }
    }

    private suspend fun clearToPatientQueries(id: String) {
        withContext(Dispatchers.IO){
            dao.clearToPatient(id)
        }
    }
}