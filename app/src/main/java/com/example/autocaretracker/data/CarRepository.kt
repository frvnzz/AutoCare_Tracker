package com.example.autocaretracker.data

import kotlinx.coroutines.flow.Flow

class CarRepository(private val carDao: CarDao) {
    val allCars: Flow<List<Car>> = carDao.getAllCars()

    suspend fun insert(car: Car) {
        carDao.insert(car)
    }

    suspend fun delete(carId: Int) {
        carDao.delete(carId)
    }
}