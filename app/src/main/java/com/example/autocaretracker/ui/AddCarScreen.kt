package com.example.autocaretracker.ui

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
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
        val context = LocalContext.current

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    imagePath = uri.toString()
                }
            }
        }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        BackHandler {
            navController.navigate("view_cars") {
                popUpTo("add_car") { inclusive = true }
                launchSingleTop = true
                anim {
                    enter = 0
                    exit = 0
                }
            }
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
                        .focusRequester(focusRequester)
                        .padding(vertical = 8.dp),
                    keyboardOptions = KeyboardOptions.Default,
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus(force = true) })
                )

                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_PICK).apply {
                            type = "image/*"
                        }
                        launcher.launch(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Select Image")
                }

                OutlinedTextField(
                    value = latestMileage,
                    onValueChange = { latestMileage = it },
                    label = { Text("Mileage") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus(force = true) })
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            // fab for save
            FloatingActionButton(
                onClick = {
                    if (carName.isNotEmpty() && latestMileage.isNotEmpty()) {
                        try {
                            val mileage = latestMileage.trim().toInt()
                            carViewModel.insert(Car(name = carName, imagePath = imagePath, latestMileage = mileage))
                            navController.navigate("view_cars") {
                                popUpTo("add_car") { inclusive = true }
                                launchSingleTop = true
                                anim {
                                    enter = 0
                                    exit = 0
                                }
                            }
                        } catch (e: NumberFormatException) {
                            // erroor
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
                    modifier = Modifier.size(36.dp) // Icon size inside button
                )
            }
        }
    }
}
