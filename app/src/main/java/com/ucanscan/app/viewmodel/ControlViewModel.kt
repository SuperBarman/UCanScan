package com.ucanscan.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ucanscan.app.data.DatosEjemplo
import com.ucanscan.app.data.local.StockEntity

// Informacion que mostraremos en cada alerta.
data class AlertaStock(
    val producto: String,
    val sucursal: String,
    val cantidad: Int,
    val stockMinimo: Int
)

class ControlViewModel : ViewModel() {

    val sucursales = DatosEjemplo.sucursales

    var sucursalSeleccionada by mutableStateOf("TODAS")
        private set

    fun seleccionarSucursal(id: String) {
        sucursalSeleccionada = id
    }

    // Obtiene los productos con stock bajo o agotado.
    fun obtenerAlertas(stocksRoom: List<StockEntity>): List<AlertaStock> = stocksRoom
            .filter { stock ->
                val bajoMinimo = stock.cantidad <= stock.stockMinimo

                val sucursalCorrecta =
                    sucursalSeleccionada == "TODAS" ||
                    stock.sucursalId == sucursalSeleccionada

                bajoMinimo && sucursalCorrecta
            }
            .map { stock ->
                val producto = DatosEjemplo.productos
                    .firstOrNull { it.id == stock.productoId }

                val sucursal = DatosEjemplo.sucursales
                    .firstOrNull { it.id == stock.sucursalId }

                AlertaStock(
                    producto = producto?.nombre ?: "Producto desconocido",
                    sucursal = sucursal?.nombre ?: "Sucursal desconocida",
                    cantidad = stock.cantidad,
                    stockMinimo = stock.stockMinimo
                )
            }
}