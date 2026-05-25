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
import androidx.compose.material3.MaterialTheme
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
import com.example.fitlifeapp.network.AlimentoDto
import com.example.fitlifeapp.viewmodel.AlimentosViewModel

private val PurplePrimary = Color(0xFF7C3AED)
private val PurpleDark = Color(0xFF4C1D95)
private val BackgroundDark = Color(0xFF0F0A1E)
private val BackgroundCard = Color(0xFF1E1535)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlimentosScreen(
    vm: AlimentosViewModel,
    onBack: (() -> Unit)? = null
) {
    val alimentos by vm.alimentos.collectAsState()
    val loading by vm.loading.collectAsState()
    val message by vm.message.collectAsState()

    LaunchedEffect(Unit) {
        vm.cargarAlimentos()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Alimentos") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(BackgroundDark, PurpleDark)
                    )
                )
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when {
                loading -> CircularProgressIndicator()
                else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(alimentos) { alimento ->
                        AlimentoCard(alimento)
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
private fun AlimentoCard(alimento: AlimentoDto) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundCard)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = alimento.nombre, color = Color.White, style = MaterialTheme.typography.titleMedium)
            Text(text = "Calorías: ${alimento.calorias}", color = Color.White)
            Text(text = "Proteínas: ${alimento.proteinas}", color = Color.White)
            Text(text = "Carbohidratos: ${alimento.carbohidratos}", color = Color.White)
            Text(text = "Grasas: ${alimento.grasas}", color = Color.White)
        }
    }
}