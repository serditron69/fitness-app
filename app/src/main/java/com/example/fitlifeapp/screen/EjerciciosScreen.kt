package com.example.fitlifeapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitlifeapp.network.EjercicioDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EjerciciosScreen(
    ejercicios: List<EjercicioDto> = emptyList()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ejercicios") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(ejercicios) { ejercicio ->
                EjercicioItem(ejercicio)
            }
        }
    }
}

@Composable
private fun EjercicioItem(ejercicio: EjercicioDto) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = ejercicio.nombre,
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = "Grupo muscular: ${ejercicio.grupoMuscular}")
            Text(text = "Tipo: ${ejercicio.tipo}")
            Text(text = "Descripción: ${ejercicio.descripcion ?: "Sin descripción"}")
            Text(text = "Equipamiento: ${ejercicio.equipamiento ?: "Sin equipamiento"}")
        }
    }
}