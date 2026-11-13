package com.ucanscan.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucanscan.app.ui.components.AppTopBar
import com.ucanscan.app.ui.components.HistorialRoomPanel
import com.ucanscan.app.viewmodel.ControlViewModel
import com.ucanscan.app.viewmodel.StockRoomViewModel

@Composable
fun ControlScreen(
    onBack: () -> Unit,
    controlViewModel: ControlViewModel = viewModel(),
    stockRoomViewModel: StockRoomViewModel = viewModel()
) {
    val stocks by stockRoomViewModel.stocks.collectAsState(initial = emptyList())
    val alertas = controlViewModel.obtenerAlertas(stocks)

    val opciones = listOf("Todas" to "TODAS") +
        controlViewModel.sucursales.map { sucursal ->
            sucursal.nombre to sucursal.id
        }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Control y alertas",
                onBack = onBack
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Alertas de inventario",
                style = MaterialTheme.typography.headlineSmall
            )

            Text("Productos que necesitan atencion.")

            Text(
                text = "Filtrar por sucursal",
                style = MaterialTheme.typography.titleMedium
            )

            // Elegir una sucursal o mostrar todas.
            opciones.forEach { (nombre, id) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = controlViewModel.sucursalSeleccionada == id,
                        onClick = {
                            controlViewModel.seleccionarSucursal(id)
                        }
                    )

                    Text(nombre)
                }
            }

            Text(
                text = "Alertas encontradas: ${alertas.size}",
                style = MaterialTheme.typography.titleMedium
            )

            if (alertas.isEmpty()) {
                Text("No hay alertas para esta sucursal.")
            }

            // Mostrar una tarjeta por cada alerta.
            alertas.forEach { alerta ->

                val sinStock = alerta.cantidad == 0
                val estado = if (sinStock) {
                    "Sin stock"
                } else {
                    "Stock bajo"
                }

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = alerta.producto,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(alerta.sucursal)

                        Text(
                            text = estado,
                            color = if (sinStock) {
                                MaterialTheme.colorScheme.error
                            } else {
                                MaterialTheme.colorScheme.primary
                            }
                        )

                        Text("Cantidad actual: ${alerta.cantidad}")
                        Text("Stock minimo: ${alerta.stockMinimo}")
                    }
                }
            }

            Spacer(Modifier.height(8.dp))


            HistorialRoomPanel()

            }
    }
}