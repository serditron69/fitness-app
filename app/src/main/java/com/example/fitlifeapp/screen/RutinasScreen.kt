package com.example.fitlifeapp.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitlifeapp.network.RutinaDto
import com.example.fitlifeapp.viewmodel.RutinasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutinasScreen(
    vm: RutinasViewModel,
    idUsuario: Long = 1L,
    onOpenRutina: () -> Unit
) {
    val rutinas by vm.rutinas.collectAsState()
    val loading by vm.loading.collectAsState()
    val message by vm.message.collectAsState()

    LaunchedEffect(idUsuario) {
        vm.cargarRutinas(idUsuario)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Rutinas") }) }
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
private fun RutinaItem(
    rutina: RutinaDto,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = rutina.nombre,
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = rutina.descripcion ?: rutina.objetivo)
            Text(text = "Nivel: ${rutina.nivel}")
        }
    }
}