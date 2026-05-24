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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitlifeapp.network.AlimentoDto
import com.example.fitlifeapp.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    vm: HomeViewModel
) {
    val alimentos by vm.alimentos.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Alimentos") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(alimentos) { alimento ->
                AlimentoItem(alimento)
            }
        }
    }
}

@Composable
fun AlimentoItem(alimento: AlimentoDto) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = alimento.nombre,
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = "Calorías: ${alimento.calorias ?: 0}")
            Text(text = "Proteínas: ${alimento.proteinas ?: 0.0}")
            Text(text = "Carbohidratos: ${alimento.carbohidratos ?: 0.0}")
            Text(text = "Grasas: ${alimento.grasas ?: 0.0}")
        }
    }
}