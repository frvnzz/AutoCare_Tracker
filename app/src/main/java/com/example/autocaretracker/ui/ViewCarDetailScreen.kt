package com.example.autocaretracker.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import com.example.autocaretracker.ui.theme.AutoCareTrackerTheme

@Composable
fun ViewCarDetailScreen(navController: NavController, carId: Int, carRepository: CarRepository) {
    AutoCareTrackerTheme {
        val carViewModel: CarViewModel = viewModel(factory = CarViewModelFactory(carRepository))
        val carState = carViewModel.getCarById(carId).collectAsState(initial = null)
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