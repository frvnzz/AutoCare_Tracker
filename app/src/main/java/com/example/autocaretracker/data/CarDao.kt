package com.example.autocaretracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Query("SELECT * FROM cars ORDER BY name ASC")
    fun getAllCars(): Flow<List<Car>>

    @Insert
    suspend fun insert(car: Car)

    @Query("DELETE FROM cars WHERE car_id = :carId")
    suspend fun delete(carId: Int)
}