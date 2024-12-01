package com.example.lecture_2.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.lecture_2.R
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lecture_2.SecondActivity

enum class Screens {
    Home,
    Overview,
    Settings
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello, $name!",
        modifier = modifier.padding(16.dp)
    )
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(navController, Screens.Home.name, Modifier.padding(innerPadding)) {
            composable(Screens.Home.name) {
                Greeting(
                    name = "Android",
                    modifier = Modifier.padding(innerPadding)
                )
            }
            composable(Screens.Overview.name) {
                Column {
                    Calculation()
                    Button(onClick = {
                        val intent = Intent(context, SecondActivity::class.java)
                        intent.putExtra("data-from-first", 256.0)
                        context.startActivity(intent)
                    }) {
                        Text("Navigate to second activity")
                    }
                }
                Calculation()
            }
            composable(Screens.Settings.name) {
                ResourceBox()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResourceBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(dimensionResource(id = R.dimen.box_width))
            .height(dimensionResource(id = R.dimen.box_height))
            .background(colorResource(id = R.color.emerald_green)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(id = R.string.resources_text))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewResourceBox() {
    ResourceBox()
}

@Composable
fun Calculation() {
    var input1Text by remember { mutableStateOf("") }
    var input2Text by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = input1Text,
            onValueChange = { input1Text = it },
            label = { Text("Number 1") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = input2Text,
            onValueChange = { input2Text = it },
            label = { Text("Number 2") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                val num1 = input1Text.toDoubleOrNull() ?: 0.0
                val num2 = input2Text.toDoubleOrNull() ?: 0.0
                val sum = num1 + num2
                resultText = "Sum: $sum"
            }) {
                Text("Add")
            }
            Button(onClick = {
                val num1 = input1Text.toDoubleOrNull() ?: 0.0
                val num2 = input2Text.toDoubleOrNull() ?: 0.0
                val difference = num1 - num2
                resultText = "Difference: $difference"
            }) {
                Text("Subtract")
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                val num1 = input1Text.toDoubleOrNull() ?: 0.0
                val num2 = input2Text.toDoubleOrNull() ?: 0.0
                val product = num1 * num2
                resultText = "Product: $product"
            }) {
                Text("Multiply")
            }
            Button(onClick = {
                val num1 = input1Text.toDoubleOrNull() ?: 0.0
                val num2 = input2Text.toDoubleOrNull() ?: 0.0
                val quotient = if (num2 != 0.0) num1 / num2 else "Error: Division by zero"
                resultText = "Quotient: $quotient"
            }) {
                Text("Divide")
            }
        }
        Text(resultText, modifier = Modifier.padding(top = 16.dp))
    }
}


@Composable
fun EmeraldBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(dimensionResource(id = R.dimen.box_width))
            .height(dimensionResource(id = R.dimen.box_height))
            .background(colorResource(id = R.color.emerald_green)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(id = R.string.resources_text))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEmeraldBox() {
    EmeraldBox()
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val currentScreen = Screens.valueOf(currentRoute ?: Screens.Home.name)

    NavigationBar {
        NavigationBarItem(
            selected = Screens.Home == currentScreen,
            onClick = { navController.navigate(Screens.Home.name) },
            icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = Screens.Overview == currentScreen,
            onClick = { navController.navigate(Screens.Overview.name) },
            icon = { Icon(imageVector = Icons.AutoMirrored.Default.List, contentDescription = "Overview") },
            label = { Text("Overview") }
        )
        NavigationBarItem(
            selected = Screens.Settings == currentScreen,
            onClick = { navController.navigate(Screens.Settings.name) },
            icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") }
        )
    }
}
