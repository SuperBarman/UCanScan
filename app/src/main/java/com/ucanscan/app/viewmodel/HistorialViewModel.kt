package com.ucanscan.app.viewmodel

import androidx.lifecycle.ViewModel
import com.ucanscan.app.data.DatosControlEjemplo
import com.ucanscan.app.data.DatosEjemplo
import com.ucanscan.app.model.TipoMovimiento

// Representa una diferencia de inventario.
data class Discrepancia(
    val producto: String,
    val sucursal: String,
    val registrado: Int,
    val contado: Int
)

class HistorialViewModel : ViewModel() {

    val movimientos = DatosControlEjemplo.movimientos

    // Compara conteos fisicos con stock registrado.
    fun obtenerDiscrepancias(): List<Discrepancia> {

        return movimientos
            .filter { it.tipo == TipoMovimiento.CONTEO }
            .mapNotNull { movimiento ->

                val stock = DatosEjemplo.stocks.firstOrNull {
                    it.productoId == movimiento.productoId &&
                    it.sucursalId == movimiento.sucursalId
                }

                if (stock == null ||
                    stock.cantidad == movimiento.cantidad
                ) {
                    null
                } else {
                    val producto = DatosEjemplo.productos
                        .firstOrNull { it.id == movimiento.productoId }

                    val sucursal = DatosEjemplo.sucursales
                        .firstOrNull { it.id == movimiento.sucursalId }

                    Discrepancia(
                        producto = producto?.nombre ?: "Desconocido",
                        sucursal = sucursal?.nombre ?: "Desconocida",
                        registrado = stock.cantidad,
                        contado = movimiento.cantidad
                    )
                }
            }
    }
}