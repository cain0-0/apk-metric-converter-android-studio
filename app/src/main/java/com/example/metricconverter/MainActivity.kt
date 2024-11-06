package com.example.metricconverter

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.metricconverter.ui.theme.MetricConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MetricConverterTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) { innerPadding ->
                    ConverterApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ConverterApp(modifier: Modifier = Modifier) {
    var input by remember { mutableStateOf("") }
    var fromUnit by remember { mutableStateOf("Celsius") }
    var toUnit by remember { mutableStateOf("Fahrenheit") }
    var result by remember { mutableStateOf("") }
    val units = listOf("Celsius", "Fahrenheit", "Kelvin")

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF003366)) // Ganti dengan warna biru tua
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Metric Converter",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
                Text(
                    text = "Nama : Matthew Sumampouw / 220211060097",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
        }


        // Body
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE3F2FD))
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Input for Value
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    label = { Text("Enter temperature") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Dropdown for From Unit
                UnitDropdown(label = "From Unit", selectedUnit = fromUnit, units = units) {
                    fromUnit = it
                }

                // Dropdown for To Unit
                UnitDropdown(label = "To Unit", selectedUnit = toUnit, units = units) {
                    toUnit = it
                }

                Button(
                    onClick = {
                        result = convertTemperature(input, fromUnit, toUnit)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Convert")
                }

                if (result.isNotEmpty()) {
                    Text(
                        text = "Result: $result $toUnit",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun UnitDropdown(label: String, selectedUnit: String, units: List<String>, onUnitSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "$label: $selectedUnit")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            units.forEach { unit ->
                DropdownMenuItem(
                    onClick = {
                        onUnitSelected(unit)
                        expanded = false
                    },
                    text = { Text(unit) }
                )
            }
        }
    }
}

@SuppressLint("DefaultLocale")
fun convertTemperature(value: String, fromUnit: String, toUnit: String): String {
    return try {
        val inputValue = value.toDouble()

        // Temperature conversion logic
        return when (fromUnit) {
            "Celsius" -> when (toUnit) {
                "Fahrenheit" -> ((inputValue * 9/5) + 32).toInt().toString()
                "Kelvin" -> (inputValue + 273.15).toInt().toString()
                else -> inputValue.toInt().toString()
            }
            "Fahrenheit" -> when (toUnit) {
                "Celsius" -> ((inputValue - 32) * 5/9).toInt().toString()
                "Kelvin" -> ((inputValue - 32) * 5/9 + 273.15).toInt().toString()
                else -> inputValue.toInt().toString()
            }
            "Kelvin" -> when (toUnit) {
                "Celsius" -> (inputValue - 273.15).toInt().toString()
                "Fahrenheit" -> ((inputValue - 273.15) * 9/5 + 32).toInt().toString()
                else -> inputValue.toInt().toString()
            }
            else -> "Invalid unit"
        }
    } catch (e: NumberFormatException) {
        "Invalid input"
    }
}

@Preview(showBackground = true)
@Composable
fun ConverterAppPreview() {
    MetricConverterTheme {
        ConverterApp()
    }
}
