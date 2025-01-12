package com.example.autocaretracker.data

import kotlinx.coroutines.flow.Flow

class CarRepository(private val carDao: CarDao, private val maintenanceDao: MaintenanceDao) {
    val allCars: Flow<List<Car>> = carDao.getAllCars()

    fun getCarById(carId: Int): Flow<Car?> {
        return carDao.getCarById(carId)
    }

    suspend fun insert(car: Car) {
        carDao.insert(car)
    }

    suspend fun update(car: Car) {
        carDao.update(car)
    }

    suspend fun delete(carId: Int) {
        carDao.delete(carId)
    }

    suspend fun deleteCarAndMaintenance(carId: Int) {
        carDao.delete(carId)
        maintenanceDao.deleteMaintenanceForCar(carId)
    }
}