package com.example.fitlifeapp.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitlifeapp.network.EjercicioDto
import com.example.fitlifeapp.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EjerciciosScreen(vm: HomeViewModel = viewModel()) {
    val state by vm.uiState.collectAsStateWithLifecycle()

    var search by remember { mutableStateOf("") }
    var tipoSeleccionado by remember { mutableStateOf("Todos") }
    var grupoSeleccionado by remember { mutableStateOf("Todos") }
    var ejercicioSeleccionado by remember { mutableStateOf<EjercicioDto?>(null) }

    val ejercicios = state.ejercicios

    val tipos = listOf("Todos") + ejercicios.map { it.tipo }.distinct().sorted()
    val grupos = listOf("Todos") + ejercicios.map { it.grupoMuscular }.distinct().sorted()

    val ejerciciosFiltrados = ejercicios.filter { ejercicio ->
        val coincideTexto = search.isBlank() ||
                ejercicio.nombre.contains(search, ignoreCase = true) ||
                ejercicio.grupoMuscular.contains(search, ignoreCase = true) ||
                ejercicio.tipo.contains(search, ignoreCase = true)

        val coincideTipo = tipoSeleccionado == "Todos" || ejercicio.tipo == tipoSeleccionado
        val coincideGrupo = grupoSeleccionado == "Todos" || ejercicio.grupoMuscular == grupoSeleccionado

        coincideTexto && coincideTipo && coincideGrupo
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
                    text = "Ejercicios",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Explora ejercicios por tipo y grupo muscular.",
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
                    Text("Buscar ejercicio")
                }
            )
        }

        item {
            Text(
                text = "Filtrar por tipo",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tipos.take(4).forEach { tipo ->
                    FilterChip(
                        selected = tipoSeleccionado == tipo,
                        onClick = { tipoSeleccionado = tipo },
                        label = { Text(tipo) }
                    )
                }
            }
        }

        item {
            Text(
                text = "Filtrar por grupo muscular",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
        }

        item {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.heightIn(max = 120.dp)
            ) {
                items(grupos) { grupo ->
                    FilterChip(
                        selected = grupoSeleccionado == grupo,
                        onClick = { grupoSeleccionado = grupo },
                        label = { Text(grupo) }
                    )
                }
            }
        }

        if (state.loading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        if (!state.loading && ejerciciosFiltrados.isEmpty()) {
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
                            text = "No hay ejercicios",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Prueba cambiando los filtros o revisa la conexión con el backend.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        items(ejerciciosFiltrados) { ejercicio ->
            EjercicioCard(
                ejercicio = ejercicio,
                onVerClick = { ejercicioSeleccionado = ejercicio }
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

    ejercicioSeleccionado?.let { ejercicio ->
        AlertDialog(
            onDismissRequest = { ejercicioSeleccionado = null },
            confirmButton = {
                TextButton(onClick = { ejercicioSeleccionado = null }) {
                    Text("Cerrar")
                }
            },
            title = {
                Text(ejercicio.nombre)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Grupo muscular: ${ejercicio.grupoMuscular}")
                    Text("Tipo: ${ejercicio.tipo}")
                    Text("Descripción: ${ejercicio.descripcion ?: "Sin descripción"}")
                    Text("Equipamiento: ${ejercicio.equipamiento ?: "No especificado"}")
                }
            }
        )
    }
}

@Composable
fun EjercicioCard(
    ejercicio: EjercicioDto,
    onVerClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Default.FitnessCenter,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = ejercicio.nombre,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = ejercicio.grupoMuscular,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = ejercicio.tipo,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )
                    ejercicio.descripcion?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            AssistChip(
                onClick = onVerClick,
                label = { Text("Ver") }
            )
        }
    }
}