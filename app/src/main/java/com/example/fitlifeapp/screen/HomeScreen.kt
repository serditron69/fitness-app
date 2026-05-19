package com.example.fitlifeapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LocalDrink
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.RunCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitlifeapp.components.MacroBar
import com.example.fitlifeapp.components.SummaryCard
import com.example.fitlifeapp.network.AlimentoDto
import com.example.fitlifeapp.network.EjercicioDto
import com.example.fitlifeapp.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(vm: HomeViewModel = viewModel()) {
    val state by vm.uiState.collectAsStateWithLifecycle()
    var query by remember { mutableStateOf("") }
    var ejercicioSeleccionado by remember { mutableStateOf<EjercicioDto?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { vm.buscarAlimentos(query) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        "Hoy",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        "FitLife",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Estilo inspirado en MyFitnessPal: diario, calorías, macros y búsqueda rápida.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(Color(0xFF1AB15F), Color(0xFF0C8B49))
                            ),
                            shape = RoundedCornerShape(28.dp)
                        )
                        .padding(20.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            "Calorías restantes",
                            color = Color.White.copy(alpha = 0.9f),
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(
                            text = (state.metaCalorias - state.caloriasConsumidas).toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold
                        )
                        LinearProgressIndicator(
                            progress = { state.caloriasConsumidas.toFloat() / state.metaCalorias.toFloat() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp),
                            color = Color.White,
                            trackColor = Color.White.copy(alpha = 0.2f)
                        )
                        Text(
                            "${state.caloriasConsumidas} consumidas de ${state.metaCalorias}",
                            color = Color.White.copy(alpha = 0.88f)
                        )
                    }
                }
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SummaryCard(
                        "Agua",
                        "${state.aguaVasos} vasos",
                        "Meta diaria",
                        Color(0xFF4AA3FF),
                        Modifier.weight(1f)
                    )
                    SummaryCard(
                        "Ejercicio",
                        "420 kcal",
                        "Quemadas hoy",
                        Color(0xFFFF8A3D),
                        Modifier.weight(1f)
                    )
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            "Macros",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        MacroBar("Proteínas", "92 g", Color(0xFF4AA3FF))
                        MacroBar("Carbohidratos", "155 g", Color(0xFFFFC247))
                        MacroBar("Grasas", "48 g", Color(0xFFFF7F7F))
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    label = { Text("Buscar alimento en backend / Open Food Facts") },
                    trailingIcon = {
                        TextButton(onClick = { vm.buscarAlimentos(query) }) {
                            Text("Buscar")
                        }
                    }
                )
            }

            if (state.buscados.isNotEmpty()) {
                item {
                    Text(
                        "Resultados de alimentos",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                items(state.buscados) { item ->
                    FoodCard(item = item, onImport = { vm.importarAlimento(item) })
                }
            }

            item {
                SectionTitle("Diario de comidas", Icons.Outlined.Restaurant)
            }

            items(state.alimentos) { food ->
                DiaryFoodRow(food)
            }

            item {
                SectionTitle("Rutinas recomendadas", Icons.Outlined.RunCircle)
            }

            items(state.rutinas) { rutina ->
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            rutina.nombre,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            rutina.objetivo,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            rutina.descripcion ?: "Rutina lista para seguir hoy",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                SectionTitle("Ejercicios del día", Icons.Outlined.LocalDrink)
            }

            items(state.ejercicios) { ejercicio ->
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
                        Column(Modifier.weight(1f)) {
                            Text(ejercicio.nombre, fontWeight = FontWeight.Bold)
                            Text(
                                ejercicio.grupoMuscular,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                ejercicio.tipo,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        AssistChip(
                            onClick = { ejercicioSeleccionado = ejercicio },
                            label = { Text("Ver") }
                        )
                    }
                }
            }

            if (state.error != null) {
                item {
                    Text(
                        state.error ?: "",
                        color = MaterialTheme.colorScheme.error
                    )
                }
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
fun SectionTitle(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DiaryFoodRow(food: AlimentoDto) {
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
            Column(Modifier.weight(1f)) {
                Text(food.nombre, fontWeight = FontWeight.Bold)
                Text(
                    food.categoria,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    "P ${food.proteinas100g ?: 0}g • C ${food.carbohidratos100g ?: 0}g • G ${food.grasas100g ?: 0}g",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                "${food.calorias100g.toInt()} kcal",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun FoodCard(item: AlimentoDto, onImport: () -> Unit) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                item.nombre,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                "${item.calorias100g.toInt()} kcal por 100g",
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                "P ${item.proteinas100g ?: 0}g • C ${item.carbohidratos100g ?: 0}g • G ${item.grasas100g ?: 0}g"
            )
            Button(
                onClick = onImport,
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Importar al catálogo")
            }
        }
    }
}