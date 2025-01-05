package com.example.autocaretracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Query("SELECT * FROM cars ORDER BY name ASC")
    fun getAllCars(): Flow<List<Car>>

    @Query("SELECT * FROM cars WHERE car_id = :carId")
    fun getCarById(carId: Int): Flow<Car?>

    @Insert
    suspend fun insert(car: Car)

    @Update
    suspend fun update(car: Car)

    @Query("DELETE FROM cars WHERE car_id = :carId")
    suspend fun delete(carId: Int)
}