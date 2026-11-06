package com.ucanscan.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucanscan.app.data.DatosEjemplo
import com.ucanscan.app.model.TipoMovimiento
import com.ucanscan.app.ui.components.AppTopBar
import com.ucanscan.app.viewmodel.MovementsViewModel
import com.ucanscan.app.viewmodel.MovimientosRoomViewModel
import com.ucanscan.app.viewmodel.StockRoomViewModel
import kotlinx.coroutines.launch

@Composable
fun MovementsScreen(
    onBack: () -> Unit,
    movementsViewModel: MovementsViewModel = viewModel(),
    stockRoomViewModel: StockRoomViewModel = viewModel(),
    movimientosRoomViewModel: MovimientosRoomViewModel = viewModel()
) {
    val stocks by stockRoomViewModel.stocks.collectAsState(initial = emptyList())
    val movimientos by movimientosRoomViewModel.movimientos.collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    val tipo = remember { mutableStateOf(TipoMovimiento.ENTRADA) }
    val producto = remember { mutableStateOf(DatosEjemplo.productos.first()) }
    val sucursal = remember { mutableStateOf(DatosEjemplo.sucursales.first()) }
    var confirmar by remember { mutableStateOf(false) }
    var guardando by remember { mutableStateOf(false) }
    var mensaje by remember { mutableStateOf("") }

    val stockActual = stocks.firstOrNull {
        it.productoId == producto.value.id && it.sucursalId == sucursal.value.id
    }

    Scaffold(topBar = { AppTopBar(title = "Movimientos", onBack = onBack) }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding)
                .verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Registrar movimiento", style = MaterialTheme.typography.headlineSmall)
            Text("1. Tipo de movimiento", style = MaterialTheme.typography.titleMedium)
            TipoMovimiento.entries.forEach { opcion ->
                FilterChip(
                    selected = tipo.value == opcion,
                    onClick = { tipo.value = opcion; mensaje = "" },
                    label = { Text(opcion.name) }, modifier = Modifier.fillMaxWidth()
                )
            }

            Text("2. Producto", style = MaterialTheme.typography.titleMedium)
            DatosEjemplo.productos.forEach { opcion ->
                FilterChip(
                    selected = producto.value.id == opcion.id,
                    onClick = { producto.value = opcion; mensaje = "" },
                    label = { Text(opcion.nombre) }, modifier = Modifier.fillMaxWidth()
                )
            }

            Text("3. Sucursal", style = MaterialTheme.typography.titleMedium)
            DatosEjemplo.sucursales.forEach { opcion ->
                FilterChip(
                    selected = sucursal.value.id == opcion.id,
                    onClick = { sucursal.value = opcion; mensaje = "" },
                    label = { Text(opcion.nombre) }, modifier = Modifier.fillMaxWidth()
                )
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Stock disponible en Room", style = MaterialTheme.typography.titleMedium)
                    Text(stockActual?.cantidad?.toString() ?: "Cargando / sin registro")
                }
            }

            Text("4. Cantidad", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = movementsViewModel.cantidad,
                onValueChange = movementsViewModel::cambiarCantidad,
                label = { Text(if (tipo.value == TipoMovimiento.CONTEO) "Cantidad contada" else "Unidades") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true, modifier = Modifier.fillMaxWidth()
            )
            if (movementsViewModel.mensajeError.isNotBlank()) {
                Text(movementsViewModel.mensajeError, color = MaterialTheme.colorScheme.error)
            }
            if (mensaje.isNotBlank()) Text(mensaje)

            Button(
                enabled = stockActual != null && !guardando,
                onClick = {
                    if (movementsViewModel.validarCantidad(tipo.value, stockActual?.cantidad ?: 0)) {
                        confirmar = true
                    }
                }, modifier = Modifier.fillMaxWidth()
            ) { Text("Validar movimiento") }

            Text("Movimientos guardados en Room", style = MaterialTheme.typography.titleLarge)
            if (movimientos.isEmpty()) {
                Text("Aun no hay movimientos persistentes.")
            } else {
                movimientos.take(10).forEach { mov ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("${mov.tipo}: ${mov.cantidad} unidades")
                            Text("${DatosEjemplo.productos.firstOrNull { it.id == mov.productoId }?.nombre ?: mov.productoId} - ${DatosEjemplo.sucursales.firstOrNull { it.id == mov.sucursalId }?.nombre ?: mov.sucursalId}")
                            Text("Stock anterior: ${mov.stockAnterior} / nuevo: ${mov.stockNuevo}")
                        }
                    }
                }
            }
        }

        if (confirmar) {
            AlertDialog(
                onDismissRequest = { if (!guardando) confirmar = false },
                title = { Text("Confirmar movimiento") },
                text = {
                    Text("${tipo.value} - ${producto.value.nombre} - ${sucursal.value.nombre}\nCantidad: ${movementsViewModel.cantidad}\nSe guardara en Room.")
                },
                confirmButton = {
                    TextButton(enabled = !guardando, onClick = {
                        val numero = movementsViewModel.cantidad.toIntOrNull()
                        if (numero == null) {
                            confirmar = false
                            mensaje = "Cantidad no valida."
                        } else {
                            guardando = true
                            scope.launch {
                                try {
                                    movimientosRoomViewModel.registrar(
                                        tipo.value, producto.value.id, sucursal.value.id, numero
                                    )
                                    movementsViewModel.cambiarCantidad("")
                                    mensaje = "Movimiento guardado y stock actualizado en Room."
                                    confirmar = false
                                } catch (e: Exception) {
                                    mensaje = e.message ?: "No se pudo registrar el movimiento."
                                    confirmar = false
                                } finally {
                                    guardando = false
                                }
                            }
                        }
                    }) { Text(if (guardando) "Guardando..." else "Confirmar") }
                },
                dismissButton = {
                    TextButton(enabled = !guardando, onClick = { confirmar = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}