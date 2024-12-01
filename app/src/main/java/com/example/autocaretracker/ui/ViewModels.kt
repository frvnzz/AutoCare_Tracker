package com.example.autocaretracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.autocaretracker.data.Car
import com.example.autocaretracker.data.CarRepository
import com.example.autocaretracker.data.Maintenance
import com.example.autocaretracker.data.MaintenanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CarViewModel(private val repository: CarRepository) : ViewModel() {
    val allCars: Flow<List<Car>> = repository.allCars

    fun insert(car: Car) = viewModelScope.launch {
        repository.insert(car)
    }

    fun delete(carId: Int) = viewModelScope.launch {
        repository.delete(carId)
    }
}

class CarViewModelFactory(private val repository: CarRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MaintenanceViewModel(private val repository: MaintenanceRepository) : ViewModel() {
    fun getMaintenanceForCar(carId: Int): Flow<List<Maintenance>> = repository.getMaintenanceForCar(carId)

    fun insert(maintenance: Maintenance) = viewModelScope.launch {
        repository.insert(maintenance)
    }

    fun delete(maintenanceId: Int) = viewModelScope.launch {
        repository.delete(maintenanceId)
    }
}

class MaintenanceViewModelFactory(private val repository: MaintenanceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MaintenanceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MaintenanceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}