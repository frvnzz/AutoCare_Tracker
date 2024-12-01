package com.example.autocaretracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MaintenanceDao {
    @Query("SELECT * FROM maintenance WHERE car_id = :carId ORDER BY date DESC")
    fun getMaintenanceForCar(carId: Int): Flow<List<Maintenance>>

    @Insert
    suspend fun insert(maintenance: Maintenance)

    @Query("DELETE FROM maintenance WHERE maintenance_id = :maintenanceId")
    suspend fun delete(maintenanceId: Int)
}