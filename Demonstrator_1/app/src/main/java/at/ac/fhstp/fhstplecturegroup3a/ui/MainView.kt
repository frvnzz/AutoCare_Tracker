package at.ac.fhstp.fhstplecturegroup3a.ui

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import at.ac.fhstp.fhstplecturegroup3a.R
import at.ac.fhstp.fhstplecturegroup3a.SecondActivity
import at.ac.fhstp.fhstplecturegroup3a.ui.theme.FHSTPLectureGroup3ATheme

enum class Screens {
    Home,
    Overview
}

@Composable
fun MainApp(mainViewModel: MainViewModel = viewModel()) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val state by mainViewModel.mainUiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController) }
    )
    { innerPadding ->
        NavHost(navController, Screens.Home.name, Modifier.padding(innerPadding)) {
            composable(Screens.Home.name) {
                Greeting(
                    name = "Android",
                    modifier = Modifier.padding(innerPadding)
                )
            }
            composable(Screens.Overview.name) {
                Column {
                    Calculation(state.result, {num1Text, num2Text -> mainViewModel.onCalculate(num1Text, num2Text)})
                    Button(onClick = {
                        val intent = Intent(context, SecondActivity::class.java)
                        intent.putExtra("data-from-first", 256.0)
                        context.startActivity(intent)
                    }) {
                        Text("Navigate to second activity!")
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: Screens.Home.name
    val currentScreen = Screens.valueOf(currentRoute)

    NavigationBar {
        NavigationBarItem(
            selected = Screens.Home == currentScreen,
            onClick = { navController.navigate(Screens.Home.name)  },
            icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = Screens.Overview == currentScreen,
            onClick = { navController.navigate(Screens.Overview.name) },
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.List,
                    contentDescription = "Overview"
                )
            },
            label = { Text("Overview") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Calculation(result: Double = 0.0, onCalculate : (String, String) -> Unit = { _, _ -> }) {
    var input1Text by remember { mutableStateOf("") }
    var input2Text by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(input1Text, { input1Text = it }, label = { Text("Number 1") })
        OutlinedTextField(input2Text, { input2Text = it }, label = { Text("Number 2") })
        Button(onClick = {
            /*
            OnClick is an event, we can do side effects here
             */
//            val num1 = input1Text.toDoubleOrNull() ?: 0.0
//            val num2 = input2Text.toDoubleOrNull() ?: 0.0
//            val sum = num1 + num2

            onCalculate(input1Text, input2Text)

        }) {
            Text("Calculate!")
        }
        Text("$result")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(Modifier.background(colorResource(R.color.primary))) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Text(
            text = stringResource(R.string.greeting)
        )
        Box(
            Modifier
                .size(dimensionResource(R.dimen.smallSpacing))
                .background(Color.Green)
        )
        Image(painterResource(R.drawable.baseline_alarm_24), stringResource(R.string.alarm_clock))
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FHSTPLectureGroup3ATheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun Exercise3() {
    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Box(
                Modifier
                    .background(Color.Red)
                    .weight(0.2f)
                    .fillMaxSize()
            )
            Box(
                Modifier
                    .background(Color.White)
                    .weight(1f)
                    .fillMaxSize()
            )
        }
        Row(
            Modifier
                .weight(0.2f)
                .fillMaxWidth()
                .background(Color.Red)
        ) {

        }
        Row(
            Modifier
                .weight(1f)
                .fillMaxSize()
                .background(Color.White),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("TEXT", fontSize = 36.sp)
        }
        Row(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.Red)
        ) {

        }
    }
}