package com.ucanscan.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucanscan.app.data.DatosEjemplo
import com.ucanscan.app.data.local.MovimientoEntity
import com.ucanscan.app.viewmodel.MovimientosRoomViewModel

@Composable
fun HistorialRoomPanel(vm: MovimientosRoomViewModel = viewModel()) {
    val movimientos by vm.movimientos.collectAsState(initial = emptyList())

    fun producto(id: String) = DatosEjemplo.productos.firstOrNull { it.id == id }?.nombre ?: id
    fun sucursal(id: String) = DatosEjemplo.sucursales.firstOrNull { it.id == id }?.nombre ?: id

    Text("Historial de movimientos", style = MaterialTheme.typography.titleLarge)
    if (movimientos.isEmpty()) {
        Text("Todavia no hay movimientos registrados.")
    } else {
        movimientos.take(15).forEach { movimiento ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("${movimiento.tipo}: ${movimiento.cantidad} unidades")
                    Text("${producto(movimiento.productoId)} - ${sucursal(movimiento.sucursalId)}")
                    Text("Stock: ${movimiento.stockAnterior} -> ${movimiento.stockNuevo}")
                }
            }
        }
        if (movimientos.size > 15) Text("Se muestran los 15 movimientos mas recientes.")
    }

    val discrepancias = movimientos.filter {
        it.tipo == "CONTEO" && it.stockAnterior != it.stockNuevo
    }
    Text("Discrepancias reales de conteo: ${discrepancias.size}", style = MaterialTheme.typography.titleLarge)
    if (discrepancias.isEmpty()) {
        Text("Sin diferencias registradas en conteos.")
    } else {
        discrepancias.take(10).forEach { item ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("${producto(item.productoId)} - ${sucursal(item.sucursalId)}")
                    Text("Registrado antes del conteo: ${item.stockAnterior}")
                    Text("Contado fisicamente: ${item.stockNuevo}")
                    Text("Diferencia: ${item.stockNuevo - item.stockAnterior}")
                }
            }
        }
        if (discrepancias.size > 10) Text("Se muestran las 10 discrepancias mas recientes.")
    }
}