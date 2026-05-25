package com.example.fitlifeapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
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
import com.example.fitlifeapp.viewmodel.RegistroComidasViewModel

private val PurplePrimary = Color(0xFF7C3AED)
private val PurpleDark = Color(0xFF4C1D95)
private val BackgroundDark = Color(0xFF0F0A1E)
private val BackgroundCard = Color(0xFF1E1535)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroComidasScreen(
    vm: RegistroComidasViewModel,
    idUsuario: Long = 1L
) {
    val alimentos by vm.alimentos.collectAsState()
    val selected by vm.selectedAlimento.collectAsState()
    val cantidad by vm.cantidadGramos.collectAsState()
    val comidaTipo by vm.comidaTipo.collectAsState()
    val loading by vm.loading.collectAsState()
    val message by vm.message.collectAsState()

    LaunchedEffect(Unit) {
        vm.cargarAlimentos()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Registro de comidas") }) }
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
            Text("Selecciona un alimento", color = Color.White)

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(alimentos) { alimento ->
                    FoodSelectCard(
                        alimento = alimento,
                        selected = selected?.idAlimento == alimento.idAlimento,
                        onClick = { vm.seleccionarAlimento(alimento) }
                    )
                }
            }

            OutlinedTextField(
                value = cantidad,
                onValueChange = vm::onCantidadChange,
                label = { Text("Cantidad en gramos") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = comidaTipo,
                onValueChange = vm::onComidaTipoChange,
                label = { Text("Tipo de comida (desayuno, almuerzo, cena, snack)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { vm.registrarComida(idUsuario) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading
            ) {
                Text("Registrar comida")
            }

            message?.let {
                Text(text = it, color = Color.White)
            }
        }
    }
}

@Composable
private fun FoodSelectCard(
    alimento: AlimentoDto,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (selected) PurplePrimary else BackgroundCard)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = alimento.nombre, color = Color.White)
            Text(text = "Calorías: ${alimento.calorias}", color = Color.White)
        }
    }
}