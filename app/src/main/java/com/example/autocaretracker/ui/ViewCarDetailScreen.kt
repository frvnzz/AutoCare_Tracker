package com.example.autocaretracker.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.autocaretracker.data.CarRepository
import com.example.autocaretracker.data.Car
import com.example.autocaretracker.data.MaintenanceRepository
import com.example.autocaretracker.ui.theme.AutoCareTrackerTheme
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ViewCarDetailScreen(navController: NavController, carId: Int, carRepository: CarRepository, maintenanceRepository: MaintenanceRepository) {
    AutoCareTrackerTheme {
        val carViewModel: CarViewModel = viewModel(factory = CarViewModelFactory(carRepository))
        val maintenanceViewModel: MaintenanceViewModel = viewModel(factory = MaintenanceViewModelFactory(maintenanceRepository))
        val carState = carViewModel.getCarById(carId).collectAsState(initial = null)
        val maintenanceItems = maintenanceViewModel.getMaintenanceForCar(carId).collectAsState(initial = emptyList())

        val car = carState.value

        car?.let { carDetail: Car ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(carDetail.name, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                carDetail.imagePath?.let { imagePath ->
                    val imageUri = Uri.parse(imagePath)
                    Image(
                        painter = rememberImagePainter(data = imageUri),
                        contentDescription = "Car Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Latest Mileage: ${carDetail.latestMileage}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Maintenance History", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn {
                    items(maintenanceItems.value) { maintenance ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Task: ${maintenance.task}", style = MaterialTheme.typography.bodyLarge)
                                Text("Date: ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(maintenance.date))}", style = MaterialTheme.typography.bodySmall)
                                Text("Notes: ${maintenance.notes}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Button(onClick = { navController.popBackStack() }) {
                        Text("Back")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { navController.navigate("edit_car/$carId") }) {
                        Text("Edit")
                    }
                }
            }
        }
    }
}