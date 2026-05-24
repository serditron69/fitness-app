package com.example.ftness_app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutinaDetalleScreen(
    vm: RutinasViewModel,
    idUsuario: Long = 1L
) {
    val rutina by vm.rutinaSeleccionada.collectAsState()
    val ejercicios by vm.ejerciciosRutina.collectAsState()
    val message by vm.message.collectAsState()

    var duracion by remember { mutableStateOf("") }
    var notas by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(rutina?.nombre ?: "Detalle rutina") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(ejercicios) { item ->
                    EjercicioEditableCard(
                        item = item,
                        onSeriesChange = { vm.cambiarSeries(item.idRutinaEjercicio, it) },
                        onRepsChange = { vm.cambiarRepeticiones(item.idRutinaEjercicio, it) },
                        onPesoChange = { vm.cambiarPeso(item.idRutinaEjercicio, it) }
                    )
                }
            }

            OutlinedTextField(
                value = duracion,
                onValueChange = { duracion = it },
                label = { Text("Duración en minutos") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = notas,
                onValueChange = { notas = it },
                label = { Text("Notas") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    vm.finalizarRutina(
                        idUsuario = idUsuario,
                        duracionMin = duracion.toIntOrNull() ?: 0,
                        notas = notas
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Finalizar entrenamiento")
            }

            message?.let {
                Text(text = it)
            }

            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}

@Composable
private fun EjercicioEditableCard(
    item: RutinaEjercicioDto,
    onSeriesChange: (Int) -> Unit,
    onRepsChange: (Int) -> Unit,
    onPesoChange: (Double?) -> Unit
) {
    var pesoText by remember(item.idRutinaEjercicio) {
        mutableStateOf(item.pesoKg?.toString() ?: "")
    }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = item.ejercicio?.nombre ?: "Ejercicio",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = item.ejercicio?.grupoMuscular ?: ""
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { onSeriesChange(item.series - 1) }) {
                    Text("-")
                }
                Text(text = "Series: ${item.series}")
                Button(onClick = { onSeriesChange(item.series + 1) }) {
                    Text("+")
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { onRepsChange(item.repeticiones - 1) }) {
                    Text("-")
                }
                Text(text = "Reps: ${item.repeticiones}")
                Button(onClick = { onRepsChange(item.repeticiones + 1) }) {
                    Text("+")
                }
            }

            OutlinedTextField(
                value = pesoText,
                onValueChange = {
                    pesoText = it
                    onPesoChange(it.toDoubleOrNull())
                },
                label = { Text("Peso kg") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}