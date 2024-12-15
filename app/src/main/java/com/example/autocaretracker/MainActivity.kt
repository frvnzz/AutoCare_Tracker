package com.example.autocaretracker

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.autocaretracker.ui.AddCarScreen
import com.example.autocaretracker.ui.ViewCarsScreen
import com.example.autocaretracker.ui.ViewCarDetailScreen
import com.example.autocaretracker.ui.theme.AutoCareTrackerTheme
import com.example.autocaretracker.data.CarRepository
import com.example.autocaretracker.data.MaintenanceRepository
import com.example.autocaretracker.data.AutoCareTrackerDatabase

class MainActivity : ComponentActivity() {
    private val carDao by lazy { AutoCareTrackerDatabase.getDatabase(this).carDao() }
    private val maintenanceDao by lazy { AutoCareTrackerDatabase.getDatabase(this).maintenanceDao() }
    private val carRepository by lazy { CarRepository(carDao) }
    private val maintenanceRepository by lazy { MaintenanceRepository(maintenanceDao) }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            Log.d("MainActivity", "All permissions granted")
            startApp()
        } else {
            Log.d("MainActivity", "One or more permissions denied")
            showPermissionDeniedDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate called")
        requestPermissions()
    }

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_AUDIO
        )

        if (permissions.all { ContextCompat.checkSelfPermission(this, it) == PermissionChecker.PERMISSION_GRANTED }) {
            Log.d("MainActivity", "All permissions already granted")
            startApp()
        } else {
            Log.d("MainActivity", "Requesting permissions")
            requestPermissionLauncher.launch(permissions)
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("The app cannot function without the required permissions. Please grant the permissions in settings.")
            .setPositiveButton("OK") { _, _ ->
                finish()
            }
            .create()
            .show()
    }

    private fun startApp() {
        Log.d("MainActivity", "Starting app")
        setContent {
            AutoCareTrackerTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "view_cars") {
                        composable("view_cars") { ViewCarsScreen(navController, carRepository) }
                        composable("add_car") { AddCarScreen(navController, carRepository) }
                        composable("view_car_detail/{carId}") { backStackEntry ->
                            val carId = backStackEntry.arguments?.getString("carId")?.toInt() ?: return@composable
                            ViewCarDetailScreen(navController, carId, carRepository)
                        }
                    }
                }
            }
        }
    }
}