package com.example.fitlifeapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitlifeapp.network.RutinaDto
import com.example.fitlifeapp.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutinasScreen(vm: HomeViewModel = viewModel()) {
    val state by vm.uiState.collectAsStateWithLifecycle()

    var search by remember { mutableStateOf("") }
    var nivelSeleccionado by remember { mutableStateOf("Todos") }
    var rutinaSeleccionada by remember { mutableStateOf<RutinaDto?>(null) }

    val rutinas = state.rutinas
    val niveles = listOf("Todos") + rutinas.map { it.nivel }.distinct().sorted()

    val rutinasFiltradas = rutinas.filter { rutina ->
        val coincideTexto = search.isBlank() ||
                rutina.nombre.contains(search, ignoreCase = true) ||
                rutina.objetivo.contains(search, ignoreCase = true) ||
                (rutina.descripcion?.contains(search, ignoreCase = true) == true)

        val coincideNivel = nivelSeleccionado == "Todos" || rutina.nivel == nivelSeleccionado

        coincideTexto && coincideNivel
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Rutinas",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Explora rutinas según tu objetivo y nivel.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                label = {
                    Text("Buscar rutina")
                }
            )
        }

        item {
            Text(
                text = "Filtrar por nivel",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
        }

        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                niveles.forEach { nivel ->
                    FilterChip(
                        selected = nivelSeleccionado == nivel,
                        onClick = { nivelSeleccionado = nivel },
                        label = { Text(nivel) }
                    )
                }
            }
        }

        if (!state.loading && rutinasFiltradas.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "No hay rutinas",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Prueba cambiando los filtros o revisa la conexión.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        items(rutinasFiltradas) { rutina ->
            RutinaCard(
                rutina = rutina,
                onVerClick = { rutinaSeleccionada = rutina }
            )
        }

        if (state.error != null) {
            item {
                Text(
                    text = state.error ?: "",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }

    rutinaSeleccionada?.let { rutina ->
        AlertDialog(
            onDismissRequest = { rutinaSeleccionada = null },
            confirmButton = {
                TextButton(onClick = { rutinaSeleccionada = null }) {
                    Text("Cerrar")
                }
            },
            title = {
                Text(rutina.nombre)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Objetivo: ${rutina.objetivo}")
                    Text("Nivel: ${rutina.nivel}")
                    Text("Descripción: ${rutina.descripcion ?: "Sin descripción"}")
                    Text("Estado: ${if (rutina.activa) "Activa" else "Inactiva"}")
                }
            }
        )
    }
}

@Composable
fun RutinaCard(
    rutina: RutinaDto,
    onVerClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = rutina.nombre,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            AssistChip(
                onClick = { },
                label = {
                    RowTextWithIcon(
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Flag,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        text = rutina.objetivo
                    )
                }
            )

            AssistChip(
                onClick = { },
                label = {
                    RowTextWithIcon(
                        icon = {
                            Icon(
                                imageVector = Icons.Default.FitnessCenter,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        text = rutina.nivel
                    )
                }
            )

            Text(
                text = rutina.descripcion ?: "Rutina lista para seguir hoy",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )

            AssistChip(
                onClick = onVerClick,
                label = { Text("Ver detalle") }
            )
        }
    }
}

@Composable
fun RowTextWithIcon(
    icon: @Composable () -> Unit,
    text: String
) {
    androidx.compose.foundation.layout.Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        icon()
        Text(text)
    }
}