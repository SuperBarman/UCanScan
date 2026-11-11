package com.ucanscan.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucanscan.app.data.DatosEjemplo
import com.ucanscan.app.viewmodel.HistorialViewModel

@Composable
fun HistorialPanel(
    historialViewModel: HistorialViewModel = viewModel()
) {
    Text(
        text = "Historial de movimientos",
        style = MaterialTheme.typography.titleLarge
    )

    // Mostrar los movimientos registrados de prueba
    historialViewModel.movimientos.forEach { movimiento ->

        val producto = DatosEjemplo.productos.firstOrNull {
            it.id == movimiento.productoId
        }

        val sucursal = DatosEjemplo.sucursales.firstOrNull {
            it.id == movimiento.sucursalId
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(producto?.nombre ?: "Producto desconocido")
                Text("Sucursal: ${sucursal?.nombre ?: "Desconocida"}")
                Text("Tipo: ${movimiento.tipo}")
                Text("Cantidad: ${movimiento.cantidad}")
            }
        }

        Spacer(Modifier.height(8.dp))
    }

    Spacer(Modifier.height(16.dp))

    Text(
        text = "Discrepancias de inventario",
        style = MaterialTheme.typography.titleLarge
    )

    val discrepancias = historialViewModel.obtenerDiscrepancias()

    if (discrepancias.isEmpty()) {
        Text("No se encontraron discrepancias.")
    }

    // Mostrar las diferencias de los conteos
    discrepancias.forEach { diferencia ->

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(diferencia.producto)
                Text("Sucursal: ${diferencia.sucursal}")
                Text("Registrado: ${diferencia.registrado}")
                Text("Contado: ${diferencia.contado}")

                val diferenciaStock =
                    diferencia.contado - diferencia.registrado

                Text("Diferencia: $diferenciaStock")
            }
        }

        Spacer(Modifier.height(8.dp))
    }
}