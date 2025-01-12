package com.example.autocaretracker.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.autocaretracker.R
import com.example.autocaretracker.data.CarRepository
import com.example.autocaretracker.ui.theme.AutoCareTrackerTheme

@Composable
fun ViewCarsScreen(navController: NavController, carRepository: CarRepository) {
    AutoCareTrackerTheme {
        val carViewModel: CarViewModel = viewModel(factory = CarViewModelFactory(carRepository))
        val cars = carViewModel.allCars.collectAsState(initial = emptyList())
        var showDialog by remember { mutableStateOf(false) }
        var carToDelete by remember { mutableStateOf<Int?>(null) }

        ConfirmDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onConfirm = {
                carToDelete?.let { carViewModel.deleteCarAndMaintenance(it) }
                showDialog = false
            },
            title = "Confirm Delete",
            text = "Are you sure you want to delete this car and all its maintenance items?"
        )

        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        FloatingActionButton(
                            onClick = {
                                navController.navigate("add_car") {
                                    popUpTo("view_cars") { inclusive = true }
                                    launchSingleTop = true
                                    anim {
                                        enter = 0
                                        exit = 0
                                    }
                                }
                            },
                            modifier = Modifier.size(72.dp),
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add_car),
                                contentDescription = "Add Car",
                                modifier = Modifier.size(36.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Add car", style = MaterialTheme.typography.bodySmall)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        FloatingActionButton(
                            onClick = {
                                navController.navigate("add_maintenance")
                            },
                            modifier = Modifier.size(72.dp),
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add),
                                contentDescription = "Add Maintenance",
                                modifier = Modifier.size(36.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Add Maintenance", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Text(
                    text = "Cars",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 16.dp, top = 32.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp)
                ) {
                    items(cars.value) { car ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    navController.navigate("view_car_detail/${car.car_id}")
                                },
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                car.imagePath.let { imagePath ->
                                    val imageUri = Uri.parse(imagePath)
                                    Image(
                                        painter = rememberAsyncImagePainter(model = imageUri),
                                        contentDescription = "Car Image",
                                        modifier = Modifier
                                            .size(100.dp)
                                            .padding(end = 16.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(car.name, style = MaterialTheme.typography.bodyLarge)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("Latest Mileage: ${car.latestMileage}", style = MaterialTheme.typography.bodySmall)
                                }
                                IconButton(onClick = {
                                    carToDelete = car.car_id
                                    showDialog = true
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_delete),
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