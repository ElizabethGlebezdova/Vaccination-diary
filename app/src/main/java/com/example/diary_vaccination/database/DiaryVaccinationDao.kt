package com.example.diary_vaccination.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DiaryVaccinationDao {

    @Insert
    fun insertPatient(patient: Patient)

    @Query("UPDATE patient SET patient_name = :patientName, patient_last_name = :patientLastName, " +
            "patient_surname = :patientSurname, patient_birthday = :patientBirthday " +
            "WHERE patient_id = :patientId")
    fun updatePatient(
        patientId: String, patientName: String, patientLastName: String,
        patientSurname: String, patientBirthday: String)

    @Query("DELETE FROM patient")
    fun clearPatient()

    @Query("DELETE FROM patient where patient_id = :id")
    fun clearToPatient(id: String)

    @Query("SELECT * FROM patient WHERE patient_id = :key")
    fun getPatient(key: Long): Patient?

    @Query("SELECT * FROM patient ORDER BY patient_id DESC")
    fun getAllPatient(): LiveData<List<Patient>>

    @Query("SELECT * FROM patient ORDER BY patient_id DESC LIMIT 1")
    fun getToPatient(): Patient?

    @Insert
    fun insertVaccine(vaccine: Vaccine)

    @Query("DELETE FROM vaccine")
    fun clearVaccines()

    @Query("DELETE FROM vaccine WHERE vaccine_id = :id")
    fun clearToVaccines(id: String)

    @Query("SELECT * FROM vaccine WHERE vaccine_id = :key")
    fun getVaccine(key: Long): Vaccine?

    @Query("SELECT * FROM vaccine ORDER BY vaccine_id DESC")
    fun getAllVaccines(): LiveData<List<Vaccine>>

    @Insert
    fun insertEntry(entry: Entry)

    @Query("DELETE FROM entry")
    fun clearEntry()

    @Query("DELETE FROM entry WHERE entry_id = :id")
    fun clearToEntry(id: String)

    @Query("UPDATE entry SET patient_id = :patientId, vaccine_id = :vaccineId, " +
            "component = :component, vaccine_date = :vaccineDate, vaccine_time = :vaccineTime " +
            "WHERE entry_id = :entryId")
    fun updateEntry(
        entryId: String, patientId: String, vaccineId: String,
        component: String, vaccineDate: String, vaccineTime: String
    )

    @Query("SELECT e.entry_id, p.patient_name, p.patient_last_name, p.patient_birthday, " +
            "v.vaccine_name, e.component, e.vaccine_date, e.vaccine_time " +
            "FROM entry AS e " +
            "LEFT JOIN patient AS p ON p.patient_id = e.patient_id " +
            "LEFT JOIN vaccine AS v ON v.vaccine_id = e.vaccine_id " +
            "WHERE e.entry_id = :key")
    fun getEntry(key: Long): EntryAll?

    @Query("SELECT * FROM entry WHERE entry.entry_id = :entryId LIMIT 1")
    fun getEntryById(entryId: String): Entry?

    @Query("SELECT e.entry_id, p.patient_name, p.patient_last_name, p.patient_birthday, " +
            "v.vaccine_name, e.component, e.vaccine_date, e.vaccine_time " +
            "FROM entry AS e " +
            "LEFT JOIN patient AS p ON p.patient_id = e.patient_id " +
            "LEFT JOIN vaccine AS v ON v.vaccine_id = e.vaccine_id " +
            "ORDER BY e.entry_id DESC")
    fun getAllEntries(): LiveData<List<EntryAll>>

    @Query("DELETE FROM entry WHERE patient_id = :key")
    fun clearEntryById(key: String)

    @Query("SELECT e.entry_id, p.patient_name, p.patient_last_name, p.patient_birthday, " +
            "v.vaccine_name, e.component, e.vaccine_date, e.vaccine_time " +
            "FROM entry AS e " +
            "LEFT JOIN patient AS p ON p.patient_id = e.patient_id " +
            "LEFT JOIN vaccine AS v ON v.vaccine_id = e.vaccine_id " +
            "WHERE p.patient_last_name = :lastName")
    fun getFilteredByLastName(lastName: String): LiveData<List<EntryAll>>

    @Query("SELECT e.entry_id, p.patient_name, p.patient_last_name, p.patient_birthday, " +
            "v.vaccine_name, e.component, e.vaccine_date, e.vaccine_time " +
            "FROM entry AS e " +
            "LEFT JOIN patient AS p ON p.patient_id = e.patient_id " +
            "LEFT JOIN vaccine AS v ON v.vaccine_id = e.vaccine_id " +
            "WHERE e.vaccine_date = :vaccineDate")
    fun getFilteredByVaccineDate(vaccineDate: String): LiveData<List<EntryAll>>

    @Query("SELECT e.entry_id, p.patient_name, p.patient_last_name, p.patient_birthday, " +
            "v.vaccine_name, e.component, e.vaccine_date, e.vaccine_time " +
            "FROM entry AS e " +
            "LEFT JOIN patient AS p ON p.patient_id = e.patient_id " +
            "LEFT JOIN vaccine AS v ON v.vaccine_id = e.vaccine_id " +
            "WHERE p.patient_last_name = :lastName AND e.vaccine_date = :vaccineDate")
    fun getFilteredByLastNameAndVaccineDate(lastName: String, vaccineDate: String):
            LiveData<List<EntryAll>>
}