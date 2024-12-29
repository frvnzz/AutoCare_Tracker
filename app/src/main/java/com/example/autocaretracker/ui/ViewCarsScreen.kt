package com.example.autocaretracker.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.autocaretracker.R
import com.example.autocaretracker.data.CarRepository
import java.text.SimpleDateFormat
import java.util.*
import com.example.autocaretracker.ui.theme.AutoCareTrackerTheme
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter

@Composable
fun ViewCarsScreen(navController: NavController, carRepository: CarRepository) {
    AutoCareTrackerTheme {
        val carViewModel: CarViewModel = viewModel(factory = CarViewModelFactory(carRepository))
        val cars = carViewModel.allCars.collectAsState(initial = emptyList())

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(cars.value) { car ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                navController.navigate("view_car_detail/${car.car_id}")
                            }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            car.imagePath?.let { imagePath ->
                                val imageUri = Uri.parse(imagePath)
                                Image(
                                    painter = rememberImagePainter(data = imageUri),
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
                            IconButton(onClick = { carViewModel.delete(car.car_id) }) {
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
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .size(72.dp), // button size
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_car),
                    contentDescription = "Add Car",
                    modifier = Modifier.size(36.dp) // icon size (inside button)
                )
            }
        }
    }
}