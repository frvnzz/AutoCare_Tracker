package at.ac.fhstp.fhstplecturegroup3a

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import at.ac.fhstp.fhstplecturegroup3a.ui.theme.FHSTPLectureGroup3ATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FHSTPLectureGroup3ATheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("Enter first number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Enter second number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { calculateAndSendResult(context, num1, num2, "+") }) {
                Text("Add")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { calculateAndSendResult(context, num1, num2, "-") }) {
                Text("Subtract")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { calculateAndSendResult(context, num1, num2, "*") }) {
                Text("Multiply")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { calculateAndSendResult(context, num1, num2, "/") }) {
                Text("Divide")
            }
        }
    }
}

fun calculateAndSendResult(context: android.content.Context, num1: String, num2: String, operation: String) {
    val num1Double = num1.toDoubleOrNull() ?: 0.0
    val num2Double = num2.toDoubleOrNull() ?: 0.0
    val result = when (operation) {
        "+" -> num1Double + num2Double
        "-" -> num1Double - num2Double
        "*" -> num1Double * num2Double
        "/" -> if (num2Double != 0.0) num1Double / num2Double else 0.0
        else -> 0.0
    }

    val intent = Intent(context, SecondActivity::class.java).apply {
        putExtra("result", result)
        putExtra("num1", num1)
        putExtra("num2", num2)
        putExtra("operation", operation)
    }
    context.startActivity(intent)
}