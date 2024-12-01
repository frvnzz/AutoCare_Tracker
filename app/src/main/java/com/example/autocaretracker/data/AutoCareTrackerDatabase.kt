package com.example.autocaretracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Car::class, Maintenance::class], version = 1, exportSchema = false)
abstract class AutoCareTrackerDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
    abstract fun maintenanceDao(): MaintenanceDao

    companion object {
        @Volatile
        private var INSTANCE: AutoCareTrackerDatabase? = null

        fun getDatabase(context: Context): AutoCareTrackerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AutoCareTrackerDatabase::class.java,
                    "autocare_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}