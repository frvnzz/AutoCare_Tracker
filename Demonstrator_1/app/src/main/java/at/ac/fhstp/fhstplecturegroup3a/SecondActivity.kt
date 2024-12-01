package at.ac.fhstp.fhstplecturegroup3a

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.ac.fhstp.fhstplecturegroup3a.ui.theme.FHSTPLectureGroup3ATheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val result = intent.getDoubleExtra("result", 0.0)
        val num1 = intent.getStringExtra("num1") ?: ""
        val num2 = intent.getStringExtra("num2") ?: ""
        val operation = intent.getStringExtra("operation") ?: ""

        setContent {
            FHSTPLectureGroup3ATheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ResultScreen(result = result, num1 = num1, num2 = num2, operation = operation, onBackClick = { finish() })
                }
            }
        }
    }
}

@Composable
fun ResultScreen(result: Double, num1: String, num2: String, operation: String, onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "First Number: $num1",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Second Number: $num2",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Operation: $operation",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Result: $result",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onBackClick) {
                    Text("Back")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    FHSTPLectureGroup3ATheme {
        ResultScreen(result = 0.0, num1 = "0", num2 = "0", operation = "+", onBackClick = {})
    }
}