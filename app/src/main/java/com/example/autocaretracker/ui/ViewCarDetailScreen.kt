package com.example.autocaretracker.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.autocaretracker.R
import com.example.autocaretracker.data.CarRepository
import com.example.autocaretracker.data.Car
import com.example.autocaretracker.data.Maintenance
import com.example.autocaretracker.data.MaintenanceRepository
import com.example.autocaretracker.ui.theme.AutoCareTrackerTheme
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ViewCarDetailScreen(
    navController: NavController,
    carId: Int,
    carRepository: CarRepository,
    maintenanceRepository: MaintenanceRepository
) {
    AutoCareTrackerTheme {
        val carViewModel: CarViewModel = viewModel(factory = CarViewModelFactory(carRepository))
        val maintenanceViewModel: MaintenanceViewModel = viewModel(factory = MaintenanceViewModelFactory(maintenanceRepository))
        val carState = carViewModel.getCarById(carId).collectAsState(initial = null)
        val maintenanceItems = maintenanceViewModel.getMaintenanceForCar(carId).collectAsState(initial = emptyList())

        val car = carState.value
        var showDialog by remember { mutableStateOf(false) }
        var maintenanceToDelete by remember { mutableStateOf<Maintenance?>(null) }

        ConfirmDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onConfirm = {
                maintenanceToDelete?.let { maintenanceViewModel.delete(it.maintenance_id) }
                showDialog = false
            },
            title = "Confirm Delete",
            text = "Are you sure you want to delete this maintenance item?"
        )

        car?.let { carDetail: Car ->
            val latestMaintenance = maintenanceItems.value.maxByOrNull { it.date }
            val lastUpdatedDate = latestMaintenance?.let { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it.date)) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Car Name, Mileage, and Last Updated Date
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(carDetail.name, style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Mileage: ${carDetail.latestMileage} km",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    lastUpdatedDate?.let {
                        Text(
                            text = "Last updated: $it",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    carDetail.imagePath.let { imagePath ->
                        val imageUri = Uri.parse(imagePath)
                        Image(
                            painter = rememberAsyncImagePainter(model = imageUri),
                            contentDescription = "Car Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = { navController.navigate("edit_car/$carId") },
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_edit),
                            contentDescription = "Edit",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(
                        onClick = { showDialog = true },
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_delete),
                            contentDescription = "Delete",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Red
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Maintenance History
                Text("Maintenance", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn {
                    items(maintenanceItems.value) { maintenance ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = maintenance.task,
                                        style = MaterialTheme.typography.bodyLarge,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(maintenance.date)),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = maintenance.notes,
                                        style = MaterialTheme.typography.bodySmall,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                IconButton(
                                    onClick = {
                                        maintenanceToDelete = maintenance
                                        showDialog = true
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_delete),
                                        contentDescription = "Delete",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}