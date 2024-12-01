package com.example.autocaretracker.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.autocaretracker.R
import com.example.autocaretracker.data.Car
import com.example.autocaretracker.data.CarRepository
import com.example.autocaretracker.ui.theme.AutoCareTrackerTheme

@Composable
fun AddCarScreen(navController: NavController, carRepository: CarRepository) {
    AutoCareTrackerTheme {
        val carViewModel: CarViewModel = viewModel(factory = CarViewModelFactory(carRepository))
        var carName by remember { mutableStateOf("") }
        var imagePath by remember { mutableStateOf("") }
        var latestMileage by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { focusManager.clearFocus(force = true) }
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(
                    value = carName,
                    onValueChange = { carName = it },
                    label = { Text("Car Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions.Default,
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus(force = true) })
                )
                OutlinedTextField(
                    value = imagePath,
                    onValueChange = { imagePath = it },
                    label = { Text("Image Path") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default,
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus(force = true) })
                )
                OutlinedTextField(
                    value = latestMileage,
                    onValueChange = { latestMileage = it },
                    label = { Text("Latest Mileage") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default,
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus(force = true) })
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            FloatingActionButton(
                onClick = {
                    if (carName.isNotEmpty() && latestMileage.isNotEmpty()) {
                        carViewModel.insert(Car(name = carName, imagePath = imagePath, latestMileage = latestMileage.toInt()))
                        navController.navigate("view_cars") {
                            popUpTo("add_car") { inclusive = true }
                            launchSingleTop = true
                            anim {
                                enter = 0
                                exit = 0
                            }
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
                    painter = painterResource(id = R.drawable.ic_save_car),
                    contentDescription = "Save Car",
                    modifier = Modifier.size(36.dp) // icon size (inside button)
                )
            }
        }
    }
}