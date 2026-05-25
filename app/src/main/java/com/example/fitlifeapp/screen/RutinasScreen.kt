package com.example.fitlifeapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlifeapp.network.RutinaDto
import com.example.fitlifeapp.ui.theme.*
import com.example.fitlifeapp.viewmodel.RutinasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutinasScreen(
    vm: RutinasViewModel,
    idUsuario: Long,
    onOpenRutina: () -> Unit,
    onCrearRutina: () -> Unit,
    onVerCalorias: () -> Unit
) {
    val rutinas by vm.rutinas.collectAsState()
    val loading by vm.loading.collectAsState()

    LaunchedEffect(idUsuario) { vm.cargarRutinas(idUsuario) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Header con gradiente
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(PurpleDark, BackgroundDark)
                        )
                    )
                    .padding(24.dp)
            ) {
                Column {
                    Text(
                        text = "💪 Mis Rutinas",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Entrena con consistencia",
                        fontSize = 14.sp,
                        color = TextSecondary
                    )
                }
            }

            // Contenido principal
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                when {
                    loading -> CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = PurplePrimary
                    )

                    rutinas.isEmpty() -> Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🏃", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No hay rutinas activas",
                            color = TextSecondary,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Crea tu primera rutina con el botón +",
                            color = TextSecondary,
                            fontSize = 13.sp
                        )
                    }

                    else -> LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {
                        items(rutinas) { rutina ->
                            RutinaCard(rutina) {
                                vm.seleccionarRutina(rutina)
                                onOpenRutina()
                            }
                        }
                    }
                }
            }
        }

        // Botones flotantes
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.End
        ) {
            // Botón calorias
            FloatingActionButton(
                onClick = onVerCalorias,
                containerColor = PurpleAccent,
                contentColor = TextPrimary
            ) {
                Text("🥗", fontSize = 20.sp)
            }

            // Botón crear rutina
            FloatingActionButton(
                onClick = onCrearRutina,
                containerColor = PurplePrimary,
                contentColor = TextPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Crear rutina")
            }
        }
    }
}

@Composable
private fun RutinaCard(rutina: RutinaDto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundCard)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(PurpleDark, BackgroundCard)
                    )
                )
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = rutina.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextPrimary
                )
                Text(
                    text = rutina.descripcion ?: rutina.objetivo,
                    color = TextSecondary,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = PurplePrimary.copy(alpha = 0.3f)
                ) {
                    Text(
                        text = rutina.nivel,
                        color = PurpleLight,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}