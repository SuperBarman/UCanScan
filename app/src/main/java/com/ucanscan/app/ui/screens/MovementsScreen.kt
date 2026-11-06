
package com.ucanscan.app.ui.screens

import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.Button
import com.ucanscan.app.model.TipoMovimiento
import com.ucanscan.app.data.DatosEjemplo
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton

// ORGANIZACIÓN VISUAL
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// COMPONENTES VISUALES
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

// VIEWMODEL
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucanscan.app.viewmodel.MovementsViewModel

// COMPONENTES DE UCANSCAN
import com.ucanscan.app.ui.components.AppTopBar

// PANTALLA DE MOVIMIENTOS
@Composable
fun MovementsScreen(
    onBack: () -> Unit,
    movementsViewModel: MovementsViewModel = viewModel()
) {
    val tipoSeleccionado = remember {
        mutableStateOf(TipoMovimiento.ENTRADA)
    }
    val mostrarConfirmacion = remember {
        mutableStateOf(false)
    }
    val productoSeleccionado = remember {
        mutableStateOf(DatosEjemplo.productos.first())
    }

    val sucursalSeleccionada = remember {
        mutableStateOf(DatosEjemplo.sucursales.first())
    }

    val stockActual = DatosEjemplo.stocks.find {
        it.productoId == productoSeleccionado.value.id &&
                it.sucursalId == sucursalSeleccionada.value.id
    }

    Column {
        AppTopBar(
            title = "Movimientos",
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Text("Registrar movimiento")

            Text("Selecciona el tipo de movimiento")

            TipoMovimiento.entries.forEach { tipo ->
                Button(
                    onClick = {
                        tipoSeleccionado.value = tipo
                    }
                ) {
                    Text(
                        when (tipo) {
                            TipoMovimiento.ENTRADA -> "Entrada"
                            TipoMovimiento.SALIDA -> "Salida"
                            TipoMovimiento.CONTEO -> "Conteo"
                        }
                    )

                    Text("Selecciona un producto")

                    DatosEjemplo.productos.forEach { producto ->
                        Button(
                            onClick = {
                                productoSeleccionado.value = producto
                            }
                        ) {
                            Text(producto.nombre)
                        }
                    }

                    Text("Selecciona una sucursal")

                    DatosEjemplo.sucursales.forEach { sucursal ->
                        Button(
                            onClick = {
                                sucursalSeleccionada.value = sucursal
                            }
                        ) {
                            Text(sucursal.nombre)
                        }
                    }
                    Text("Stock disponible: ${stockActual?.cantidad ?: "Sin registro"}")

                    Button(
                        enabled = stockActual != null,
                        onClick = {
                            if (movementsViewModel.validarCantidad(
                                    tipo = tipoSeleccionado.value,
                                    stockDisponible = stockActual?.cantidad ?: 0
                                )
                            ) {
                                mostrarConfirmacion.value = true
                            }
                        }
                    ) {
                        Text("Validar movimiento")
                    }
                }
                if (mostrarConfirmacion.value) {
                    AlertDialog(
                        onDismissRequest = {
                            mostrarConfirmacion.value = false
                        },
                        title = {
                            Text("Movimiento validado")
                        },
                        text = {
                            Text(
                                "Tipo: ${tipoSeleccionado.value}\n" +
                                        "Cantidad: ${movementsViewModel.cantidad}\n" +
                                        "Producto: ${productoSeleccionado.value.nombre}\n" +
                                        "Sucursal: ${sucursalSeleccionada.value.nombre}\n\n" +
                                        "Vista previa: aún no se guarda el movimiento."
                            )
                        },

                        confirmButton = {
                            TextButton(
                                onClick = {
                                    val registrado = movementsViewModel.registrarMovimiento(
                                        tipo = tipoSeleccionado.value,
                                        productoId = productoSeleccionado.value.id,
                                        sucursalId = sucursalSeleccionada.value.id,
                                        stockDisponible = stockActual?.cantidad ?: 0
                                    )

                                    if (registrado) {
                                        mostrarConfirmacion.value = false
                                    }
                                }
                            ) {
                                Text("Confirmar movimiento")
                            }
                        }
                    )
                }
                Text("Movimientos registrados")

                if (movementsViewModel.movimientos.isEmpty()) {
                    Text("Todavía no hay movimientos registrados")
                } else {
                    movementsViewModel.movimientos.forEach { movimiento ->
                        Text(
                            "Tipo: ${movimiento.tipo}\n" +
                                    "Cantidad: ${movimiento.cantidad}\n" +
                                    "Producto: ${movimiento.productoId}\n" +
                                    "Sucursal: ${movimiento.sucursalId}"
                        )
                    }
                }
                }
            }

        }
    }