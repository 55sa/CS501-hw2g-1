package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Sliding(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Sliding(modifier: Modifier = Modifier) {
    var celsius by remember { mutableStateOf(0f) }
    var fahrenheit by remember { mutableStateOf(32f) }
    var message by remember { mutableStateOf("I wish it were warmer.") }

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Celsius: ${celsius.toInt()}°C")

        Slider(
            value = celsius,
            onValueChange = {
                celsius = it
                fahrenheit = celsiusToFahrenheit(it)
                message = updateMessage(celsius.toInt())
            },
            valueRange = 0f..100f
        )

        Text(text = "Fahrenheit: ${fahrenheit.toInt()}°F")

        Slider(
            value = fahrenheit,
            onValueChange = {
                if (it < 32f) {
                    fahrenheit = 32f
                    celsius = 0f
                } else {
                    fahrenheit = it
                    celsius = fahrenheitToCelsius(it)
                }
                message = updateMessage(celsius.toInt())
            },
            valueRange = 0f..212f
        )

        Text(text = message)
    }
}

fun celsiusToFahrenheit(celsius: Float): Float {
    return (celsius * 9 / 5) + 32
}

fun fahrenheitToCelsius(fahrenheit: Float): Float {
    return (fahrenheit - 32) * 5 / 9
}

fun updateMessage(celsius: Int): String {
    return if (celsius <= 20) {
        "I wish it were warmer."
    } else {
        "I wish it were colder."
    }
}

@Preview(showBackground = true)
@Composable
fun SlidingPreview() {
    MyApplicationTheme {
        Sliding()
    }
}
