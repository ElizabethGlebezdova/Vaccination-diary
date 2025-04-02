package com.example.diary_vaccination.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Patient::class, Vaccine::class, Entry::class],
    version = 1, exportSchema = false)
abstract class VaccinationDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: VaccinationDatabase? = null

        fun getInstance(context: Context): VaccinationDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        VaccinationDatabase::class.java, "vaccination_db")
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    abstract fun getDiaryVaccinationDao(): DiaryVaccinationDao
}