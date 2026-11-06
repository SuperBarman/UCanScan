package com.ucanscan.app.repository

import android.content.Context
import com.ucanscan.app.data.DatosEjemplo
import com.ucanscan.app.data.local.StockEntity
import kotlinx.coroutines.flow.first

class StockInicializador(context: Context) {

    private val repository = StockRepository(context)

    // Carga datos de demostracion solo si la tabla esta vacia.
    suspend fun cargarSiVacio() {

        val stocksGuardados = repository.stocks.first()

        if (stocksGuardados.isNotEmpty()) {
            return
        }

        DatosEjemplo.stocks.forEach { stock ->

            repository.guardarStock(
                StockEntity(
                    productoId = stock.productoId,
                    sucursalId = stock.sucursalId,
                    cantidad = stock.cantidad,
                    stockMinimo = stock.stockMinimo
                )
            )
        }
    }
}