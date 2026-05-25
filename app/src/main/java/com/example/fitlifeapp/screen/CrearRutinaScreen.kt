package com.example.fitlifeapp.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitlifeapp.viewmodel.RutinasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearRutinaScreen(
    vm: RutinasViewModel,
    idUsuario: Long,
    onRutinaCreada: () -> Unit,
    onVolver: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var objetivo by remember { mutableStateOf("") }
    var nivel by remember { mutableStateOf("PRINCIPIANTE") }

    val loading by vm.loading.collectAsState()
    val message by vm.message.collectAsState()

    val niveles = listOf("PRINCIPIANTE", "INTERMEDIO", "AVANZADO")
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(message) {
        if (message == "Rutina creada") onRutinaCreada()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Rutina") },
                navigationIcon = {
                    TextButton(onClick = onVolver) { Text("Volver") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = objetivo,
                onValueChange = { objetivo = it },
                label = { Text("Objetivo (ej: Fuerza, Cardio)") },
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = nivel,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Nivel") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    niveles.forEach { n ->
                        DropdownMenuItem(
                            text = { Text(n) },
                            onClick = {
                                nivel = n
                                expanded = false
                            }
                        )
                    }
                }
            }

            message?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    vm.crearRutina(
                        idUsuario = idUsuario,
                        nombre = nombre,
                        descripcion = descripcion,
                        objetivo = objetivo,
                        nivel = nivel
                    )
                },
                enabled = !loading && nombre.isNotBlank() && objetivo.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (loading) CircularProgressIndicator(modifier = Modifier.size(20.dp))
                else Text("Crear Rutina")
            }
        }
    }
}