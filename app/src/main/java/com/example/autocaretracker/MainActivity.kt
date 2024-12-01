package com.example.autocaretracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.autocaretracker.ui.AddCarScreen
import com.example.autocaretracker.ui.ViewCarsScreen
import com.example.autocaretracker.ui.theme.AutoCareTrackerTheme
import com.example.autocaretracker.data.CarRepository
import com.example.autocaretracker.data.MaintenanceRepository
import com.example.autocaretracker.data.AutoCareTrackerDatabase

class MainActivity : ComponentActivity() {
    private val carDao by lazy { AutoCareTrackerDatabase.getDatabase(this).carDao() }
    private val maintenanceDao by lazy { AutoCareTrackerDatabase.getDatabase(this).maintenanceDao() }
    private val carRepository by lazy { CarRepository(carDao) }
    private val maintenanceRepository by lazy { MaintenanceRepository(maintenanceDao) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoCareTrackerTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "view_cars") {
                        composable("view_cars") { ViewCarsScreen(navController, carRepository) }
                        composable("add_car") { AddCarScreen(navController, carRepository) }
                    }
                }
            }
        }
    }
}