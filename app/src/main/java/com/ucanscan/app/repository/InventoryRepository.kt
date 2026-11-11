package com.ucanscan.app.repository

import com.ucanscan.app.data.DatosEjemplo
import com.ucanscan.app.model.ProductoConStock
import com.ucanscan.app.data.local.StockEntity

class InventoryRepository {

    fun productosConStock(sucursalId: String, stocksRoom: List<StockEntity>): List<ProductoConStock> {
        return DatosEjemplo.productos.map { producto ->
            val stock = stocksRoom.firstOrNull {
                it.productoId == producto.id && it.sucursalId == sucursalId
            }
            ProductoConStock(
                producto = producto,
                cantidad = stock?.cantidad ?: 0,
                stockMinimo = stock?.stockMinimo ?: 0
            )
        }
    }
}