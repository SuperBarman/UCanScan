package com.ucanscan.app.repository

import android.content.Context
import androidx.room.withTransaction
import com.ucanscan.app.data.local.DatabaseProvider
import com.ucanscan.app.data.local.MovimientoEntity
import com.ucanscan.app.model.TipoMovimiento
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class MovimientosRoomRepository(context: Context) {
    private val db = DatabaseProvider.obtener(context)
    val movimientos: Flow<List<MovimientoEntity>> = db.movimientoDao().observarMovimientos()

    suspend fun registrar(
        tipo: TipoMovimiento,
        productoId: String,
        sucursalId: String,
        cantidad: Int
    ) {
        db.withTransaction {
            val actual = db.stockDao().buscarStock(productoId, sucursalId)
                ?: throw IllegalArgumentException("No existe stock para este producto y sucursal.")

            require(cantidad >= 0) { "La cantidad no puede ser negativa." }
            if (tipo != TipoMovimiento.CONTEO) {
                require(cantidad > 0) { "La cantidad debe ser mayor a cero." }
            }

            val nuevaCantidad = when (tipo) {
                TipoMovimiento.ENTRADA -> {
                    require(cantidad <= Int.MAX_VALUE - actual.cantidad) {
                        "La cantidad excede el limite permitido."
                    }
                    actual.cantidad + cantidad
                }
                TipoMovimiento.SALIDA -> {
                    require(cantidad <= actual.cantidad) {
                        "Stock insuficiente para registrar la salida."
                    }
                    actual.cantidad - cantidad
                }
                TipoMovimiento.CONTEO -> cantidad
            }

            db.stockDao().guardarStock(actual.copy(cantidad = nuevaCantidad))
            db.movimientoDao().insertar(
                MovimientoEntity(
                    id = UUID.randomUUID().toString(),
                    productoId = productoId,
                    sucursalId = sucursalId,
                    usuarioId = "OPERADOR_DEMO",
                    tipo = tipo.name,
                    cantidad = cantidad,
                    fechaMillis = System.currentTimeMillis(),
                    stockAnterior = actual.cantidad,
                    stockNuevo = nuevaCantidad
                )
            )
        }
    }
}