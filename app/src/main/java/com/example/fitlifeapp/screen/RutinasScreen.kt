package com.example.fitlifeapp.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitlifeapp.network.RutinaDto
import com.example.fitlifeapp.viewmodel.RutinasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutinasScreen(
    vm: RutinasViewModel,
    idUsuario: Long,
    onOpenRutina: () -> Unit,
    onCrearRutina: () -> Unit
) {
    val rutinas by vm.rutinas.collectAsState()
    val loading by vm.loading.collectAsState()
    val message by vm.message.collectAsState()

    LaunchedEffect(idUsuario) {
        vm.cargarRutinas(idUsuario)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Rutinas") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onCrearRutina) {
                Icon(Icons.Default.Add, contentDescription = "Crear rutina")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                when {
                    loading -> CircularProgressIndicator()
                    rutinas.isEmpty() -> Text("No hay rutinas activas")
                    else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(rutinas) { rutina ->
                            RutinaItem(rutina) {
                                vm.seleccionarRutina(rutina)
                                onOpenRutina()
                            }
                        }
                    }
                }
            }

            message?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(top = 12.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun RutinaItem(rutina: RutinaDto, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(text = rutina.nombre, style = MaterialTheme.typography.titleMedium)
            Text(text = rutina.descripcion ?: rutina.objetivo)
            Text(text = "Nivel: ${rutina.nivel}")
        }
    }
}