package com.example.diary_vaccination

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.diary_vaccination.database.DiaryVaccinationDao
import com.example.diary_vaccination.database.Entry
import com.example.diary_vaccination.database.EntryAll
import kotlinx.coroutines.*

class EntryViewModel(
    val dao: DiaryVaccinationDao,
    application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var entries: LiveData<List<EntryAll>>
    var filter = MutableLiveData<FilterData>(FilterData())
    init {
        entries = Transformations.switchMap(filter){ filter ->
            filterEntry(filter.lastName, filter.vaccineDate)
        }
    }

    fun initNewEntry(patientId: Long, vaccineId: Long, component: Long,
    vaccineDate: String, vaccineTime: String){
        uiScope.launch {
            val newEntry = Entry(patientId = patientId, vaccineId = vaccineId,
            component = component, vaccineDate = vaccineDate, vaccineTime = vaccineTime)
            insertEntry(newEntry)
        }
    }

    private suspend fun insertEntry(newEntry: Entry) {
        withContext(Dispatchers.IO){
            dao.insertEntry(newEntry)
        }
    }

    fun getEntryById(entryId: String): LiveData<Entry?> {
        val entryData = MutableLiveData<Entry?>()
        uiScope.launch {
            entryData.postValue(getEntryByIdFromDb(entryId))
        }
        return entryData
    }

    private suspend fun getEntryByIdFromDb(entryId: String): Entry? {
        return withContext(Dispatchers.IO) {
            dao.getEntryById(entryId)
        }
    }


    fun updateEntry(entryId: String, patientId: String, vaccineId: String, component: String,
                    vaccineDate: String, vaccineTime: String){
        uiScope.launch {
            updateEntryById(entryId, patientId, vaccineId, component, vaccineDate, vaccineTime)
        }
    }

    private suspend fun updateEntryById(
        entryId: String,
        patientId: String,
        vaccineId: String,
        component: String,
        vaccineDate: String,
        vaccineTime: String
    ) {
        withContext(Dispatchers.IO){
            dao.updateEntry(entryId, patientId, vaccineId, component, vaccineDate, vaccineTime)
        }
    }

    fun clearEntryByPatientId(id: String){
        uiScope.launch {
            clearEntryByPatientIdQuery(id)
        }
    }

    private suspend fun clearEntryByPatientIdQuery(id: String) {
        withContext(Dispatchers.IO){
            dao.clearEntryById(id)
        }
    }

    fun clearAllEntries() {
        uiScope.launch {
            clearAllEntriesQueries()
        }
    }
    private suspend fun clearAllEntriesQueries() {
        withContext(Dispatchers.IO) {
            dao.clearEntry()
        }
    }

    fun clearToEntry(id: String) {
        uiScope.launch {
            clearToEntryQueries(id)
        }
    }
    private suspend fun clearToEntryQueries(id: String) {
        withContext(Dispatchers.IO) {
            dao.clearToEntry(id)
        }
    }

    private fun filterEntry(lastName: String, vaccineDate: String): LiveData<List<EntryAll>>{
        if (lastName != "" && vaccineDate == ""){
            return dao.getFilteredByLastName(lastName)
        } else if (lastName == "" && vaccineDate != ""){
            return dao.getFilteredByVaccineDate(vaccineDate)
        } else if (lastName != "" && vaccineDate != ""){
            return dao.getFilteredByLastNameAndVaccineDate(lastName, vaccineDate)
        }

        return dao.getAllEntries()
    }

    class FilterData{
        var lastName: String = ""
        var vaccineDate: String = ""
    }
}