package com.example.fitlifeapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitlifeapp.viewmodel.RutinasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearRutinaScreen(
    vm: RutinasViewModel,
    idUsuario: Long = 1L,
    onVolver: () -> Unit = {}
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var objetivo by remember { mutableStateOf("") }
    var nivel by remember { mutableStateOf("PRINCIPIANTE") }

    val message by vm.message.collectAsState()

    LaunchedEffect(message) {
        if (message == "Rutina creada") onVolver()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Rutina") },
                navigationIcon = {
                    TextButton(onClick = onVolver) { Text("Volver") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripcion") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = objetivo,
                onValueChange = { objetivo = it },
                label = { Text("Objetivo") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = nivel,
                onValueChange = { nivel = it },
                label = { Text("Nivel (PRINCIPIANTE, INTERMEDIO, AVANZADO)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    vm.crearRutina(
                        nombre = nombre,
                        descripcion = descripcion,
                        objetivo = objetivo,
                        nivel = nivel,
                        idUsuario = idUsuario
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Rutina")
            }

            message?.let { Text(it) }
        }
    }
}