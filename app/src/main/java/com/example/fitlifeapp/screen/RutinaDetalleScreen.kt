package com.example.fitlifeapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlifeapp.network.RutinaEjercicioDto
import com.example.fitlifeapp.ui.theme.*
import com.example.fitlifeapp.viewmodel.RutinasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutinaDetalleScreen(
    vm: RutinasViewModel,
    idUsuario: Long,
    onVolver: () -> Unit = {}
) {
    val rutina by vm.rutinaSeleccionada.collectAsState()
    val ejercicios by vm.ejerciciosRutina.collectAsState()
    val message by vm.message.collectAsState()
    val loading by vm.loading.collectAsState()

    var duracion by remember { mutableStateOf("") }
    var notas by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(PurpleDark, BackgroundDark)))
                    .padding(24.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = rutina?.nombre ?: "Detalle",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        TextButton(onClick = onVolver) {
                            Text("Volver", color = PurpleLight)
                        }
                    }
                    rutina?.let {
                        Text(text = it.objetivo, color = TextSecondary, fontSize = 14.sp)
                        Text(text = "Nivel: ${it.nivel}", color = PurpleLight, fontSize = 13.sp)
                    }
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(ejercicios) { item ->
                    EjercicioEditableCard(
                        item = item,
                        onSeriesChange = { vm.cambiarSeries(item.idRutinaEjercicio, it) },
                        onRepsChange = { vm.cambiarRepeticiones(item.idRutinaEjercicio, it) },
                        onPesoChange = { vm.cambiarPeso(item.idRutinaEjercicio, it) }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BackgroundCard)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = duracion,
                    onValueChange = { duracion = it },
                    label = { Text("Duracion en minutos") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PurplePrimary,
                        unfocusedBorderColor = TextSecondary,
                        focusedLabelColor = PurplePrimary,
                        cursorColor = PurplePrimary,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = notas,
                    onValueChange = { notas = it },
                    label = { Text("Notas") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PurplePrimary,
                        unfocusedBorderColor = TextSecondary,
                        focusedLabelColor = PurplePrimary,
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
                        fontSize = 13.sp
                    )
                }

                Button(
                    onClick = {
                        vm.finalizarRutina(
                            idUsuario = idUsuario,
                            duracionMin = duracion.toIntOrNull() ?: 0,
                            notas = notas
                        )
                    },
                    enabled = !loading && duracion.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
                ) {
                    if (loading) CircularProgressIndicator(modifier = Modifier.size(20.dp))
                    else Text("Finalizar entrenamiento", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun EjercicioEditableCard(
    item: RutinaEjercicioDto,
    onSeriesChange: (Int) -> Unit,
    onRepsChange: (Int) -> Unit,
    onPesoChange: (Double?) -> Unit
) {
    var pesoText by remember(item.idRutinaEjercicio) {
        mutableStateOf(item.pesoKg?.toString() ?: "")
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundCard)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = item.ejercicio?.nombre ?: "Ejercicio",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            item.ejercicio?.grupoMuscular?.let {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = PurplePrimary.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = it,
                        color = PurpleLight,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Series:", color = TextSecondary, fontSize = 14.sp, modifier = Modifier.width(70.dp))
                IconButton(
                    onClick = { onSeriesChange(item.series - 1) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Text("-", color = PurpleLight, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                Text(
                    text = "${item.series}",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.width(32.dp)
                )
                IconButton(
                    onClick = { onSeriesChange(item.series + 1) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Text("+", color = PurpleLight, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }

            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Reps:", color = TextSecondary, fontSize = 14.sp, modifier = Modifier.width(70.dp))
                IconButton(
                    onClick = { onRepsChange(item.repeticiones - 1) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Text("-", color = PurpleLight, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                Text(
                    text = "${item.repeticiones}",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.width(32.dp)
                )
                IconButton(
                    onClick = { onRepsChange(item.repeticiones + 1) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Text("+", color = PurpleLight, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }

            OutlinedTextField(
                value = pesoText,
                onValueChange = {
                    pesoText = it
                    onPesoChange(it.toDoubleOrNull())
                },
                label = { Text("Peso (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PurplePrimary,
                    unfocusedBorderColor = TextSecondary,
                    focusedLabelColor = PurplePrimary,
                    cursorColor = PurplePrimary,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}