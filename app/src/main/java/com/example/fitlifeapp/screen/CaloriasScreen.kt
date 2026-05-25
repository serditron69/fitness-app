package com.example.fitlifeapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.fitlifeapp.network.AlimentoDto
import com.example.fitlifeapp.ui.theme.*
import com.example.fitlifeapp.viewmodel.CaloriasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaloriasScreen(
    vm: CaloriasViewModel,
    idUsuario: Long,
    onVolver: () -> Unit
) {
    val alimentos by vm.alimentos.collectAsState()
    val comidasHoy by vm.comidasHoy.collectAsState()
    val caloriasHoy by vm.caloriasHoy.collectAsState()
    val loading by vm.loading.collectAsState()
    val message by vm.message.collectAsState()

    var busqueda by remember { mutableStateOf("") }
    var alimentoSeleccionado by remember { mutableStateOf<AlimentoDto?>(null) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        vm.cargarAlimentos()
        vm.cargarComidasHoy(idUsuario)
    }

    val alimentosFiltrados = remember(busqueda, alimentos) {
        if (busqueda.isBlank()) alimentos
        else alimentos.filter { it.nombre.lowercase().contains(busqueda.lowercase()) }
    }

    if (mostrarDialogo && alimentoSeleccionado != null) {
        DialogRegistrarComida(
            alimento = alimentoSeleccionado!!,
            onConfirmar = { cantidad ->
                vm.registrarComida(idUsuario, alimentoSeleccionado!!, cantidad)
                mostrarDialogo = false
                alimentoSeleccionado = null
            },
            onCancelar = {
                mostrarDialogo = false
                alimentoSeleccionado = null
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(PurpleDark, BackgroundDark)))
                    .padding(24.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🥗 Calorias",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        TextButton(onClick = onVolver) {
                            Text("Volver", color = PurpleLight)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Resumen de calorias del dia
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceColor)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Calorias de hoy",
                                    color = TextSecondary,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = "${caloriasHoy.toInt()} kcal",
                                    color = PurpleLight,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "Comidas hoy",
                                    color = TextSecondary,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = "${comidasHoy.size}",
                                    color = TextPrimary,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Buscador
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                placeholder = { Text("Buscar alimento...", color = TextSecondary) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = PurplePrimary) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PurplePrimary,
                    unfocusedBorderColor = TextSecondary,
                    cursorColor = PurplePrimary,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                shape = RoundedCornerShape(12.dp)
            )

            message?.let {
                Text(
                    text = it,
                    color = if (it.startsWith("Error")) ErrorColor else SuccessColor,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontSize = 13.sp
                )
            }

            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    color = PurplePrimary
                )
            }

            // Lista de alimentos
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(alimentosFiltrados) { alimento ->
                    AlimentoCard(
                        alimento = alimento,
                        onClick = {
                            alimentoSeleccionado = alimento
                            mostrarDialogo = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun AlimentoCard(alimento: AlimentoDto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundCard)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = alimento.nombre,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MacroChip("P: ${alimento.proteinas.toInt()}g", PurplePrimary)
                    MacroChip("C: ${alimento.carbohidratos.toInt()}g", PurpleAccent)
                    MacroChip("G: ${alimento.grasas.toInt()}g", ErrorColor)
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${alimento.calorias.toInt()}",
                    color = PurpleLight,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
                Text(text = "kcal", color = TextSecondary, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun MacroChip(texto: String, color: androidx.compose.ui.graphics.Color) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = color.copy(alpha = 0.2f)
    ) {
        Text(
            text = texto,
            color = color,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Composable
private fun DialogRegistrarComida(
    alimento: AlimentoDto,
    onConfirmar: (Double) -> Unit,
    onCancelar: () -> Unit
) {
    var cantidad by remember { mutableStateOf("1") }

    Dialog(onDismissRequest = onCancelar) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = BackgroundCard)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = alimento.nombre,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "${alimento.calorias.toInt()} kcal por 100g",
                    color = TextSecondary,
                    fontSize = 14.sp
                )

                OutlinedTextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text("Cantidad (porciones)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PurplePrimary,
                        unfocusedBorderColor = TextSecondary,
                        focusedLabelColor = PurplePrimary,
                        cursorColor = PurplePrimary,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                val cantidadNum = cantidad.toDoubleOrNull() ?: 0.0
                val caloriasTotal = alimento.calorias * cantidadNum
                Text(
                    text = "Total: ${caloriasTotal.toInt()} kcal",
                    color = PurpleLight,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onCancelar,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary)
                    ) { Text("Cancelar") }

                    Button(
                        onClick = { onConfirmar(cantidadNum) },
                        enabled = cantidadNum > 0,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
                    ) { Text("Registrar") }
                }
            }
        }
    }
}