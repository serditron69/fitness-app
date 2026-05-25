package com.example.fitlifeapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fitlifeapp.network.RegistroEntrenamientoDto
import com.example.fitlifeapp.viewmodel.ProgresoViewModel

private val PurplePrimary = Color(0xFF7C3AED)
private val PurpleDark = Color(0xFF4C1D95)
private val BackgroundDark = Color(0xFF0F0A1E)
private val BackgroundCard = Color(0xFF1E1535)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgresoScreen(
    vm: ProgresoViewModel,
    idUsuario: Long = 1L
) {
    val registros by vm.registros.collectAsState()
    val loading by vm.loading.collectAsState()
    val message by vm.message.collectAsState()

    LaunchedEffect(idUsuario) {
        vm.cargarProgreso(idUsuario)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Progreso") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(listOf(BackgroundDark, PurpleDark))
                )
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when {
                loading -> CircularProgressIndicator()
                else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(registros) { registro ->
                        RegistroCard(registro)
                    }
                }
            }

            message?.let {
                Text(text = it, color = Color.White)
            }
        }
    }
}

@Composable
private fun RegistroCard(registro: RegistroEntrenamientoDto) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundCard)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = "Fecha: ${registro.fecha}", color = Color.White)
            Text(text = "Duración: ${registro.duracionMin} min", color = Color.White)
            Text(text = "Estado: ${registro.estado}", color = Color.White)
            registro.notas?.let {
                Text(text = "Notas: $it", color = Color.White)
            }
        }
    }
}