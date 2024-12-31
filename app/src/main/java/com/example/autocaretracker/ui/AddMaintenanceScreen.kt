package com.example.autocaretracker.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.autocaretracker.R
import com.example.autocaretracker.data.Car
import com.example.autocaretracker.data.CarRepository
import com.example.autocaretracker.data.Maintenance
import com.example.autocaretracker.data.MaintenanceRepository
import com.example.autocaretracker.ui.theme.AutoCareTrackerTheme
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMaintenanceScreen(navController: NavController, carRepository: CarRepository, maintenanceRepository: MaintenanceRepository) {
    AutoCareTrackerTheme {
        val carViewModel: CarViewModel = viewModel(factory = CarViewModelFactory(carRepository))
        val cars by carViewModel.allCars.collectAsState(initial = emptyList())
        val maintenanceViewModel: MaintenanceViewModel = viewModel(factory = MaintenanceViewModelFactory(maintenanceRepository))

        var selectedCar by remember { mutableStateOf<Car?>(null) }
        var expanded by remember { mutableStateOf(false) }
        var task by remember { mutableStateOf("") }
        var date by remember { mutableStateOf("") }
        var notes by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current
        val context = LocalContext.current

        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                date = "$dayOfMonth/${month + 1}/$year"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        if (!expanded) {
                            focusManager.clearFocus(force = true)
                        }
                    }
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedCar?.name ?: "Select Car",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Car") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                            .clickable { expanded = true }
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        cars.forEach { car ->
                            DropdownMenuItem(
                                text = { Text(car.name) },
                                onClick = {
                                    selectedCar = car
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = task,
                    onValueChange = { task = it },
                    label = { Text("Task") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default,
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus(force = true) })
                )
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            focusManager.clearFocus()
                            datePickerDialog.show()
                        },
                    readOnly = true
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default,
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus(force = true) })
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            FloatingActionButton(
                onClick = {
                    if (selectedCar != null && task.isNotEmpty() && date.isNotEmpty()) {
                        val maintenance = Maintenance(
                            car_id = selectedCar!!.car_id,
                            task = task,
                            date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(date)?.time ?: 0,
                            currentMileage = 0,
                            notes = notes
                        )
                        maintenanceViewModel.insert(maintenance)
                        navController.navigate("view_cars") {
                            popUpTo("add_maintenance") { inclusive = true }
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
                    .size(72.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_save_car),
                    contentDescription = "Save Maintenance",
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}