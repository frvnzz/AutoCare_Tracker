package com.example.autocaretracker.data

import kotlinx.coroutines.flow.Flow

class MaintenanceRepository(private val maintenanceDao: MaintenanceDao) {
    fun getMaintenanceForCar(carId: Int): Flow<List<Maintenance>> = maintenanceDao.getMaintenanceForCar(carId)

    suspend fun insert(maintenance: Maintenance) {
        maintenanceDao.insert(maintenance)
    }

    suspend fun delete(maintenanceId: Int) {
        maintenanceDao.delete(maintenanceId)
    }
}