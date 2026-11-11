package com.ucanscan.app.repository

import android.content.Context
import com.ucanscan.app.data.local.DatabaseProvider
import com.ucanscan.app.data.local.StockEntity
import kotlinx.coroutines.flow.Flow

// Permite acceder a los datos de stock guardados.
class StockRepository(context: Context) {

    private val stockDao =
        DatabaseProvider.obtener(context).stockDao()

    // Observa los registros y sus cambios.
    val stocks: Flow<List<StockEntity>> =
        stockDao.observarStock()

    // Busca el stock de un producto en una sucursal.
    suspend fun buscarStock(
        productoId: String,
        sucursalId: String
    ): StockEntity? {
        return stockDao.buscarStock(productoId, sucursalId)
    }

    // Guarda un registro o actualiza uno existente.
    suspend fun guardarStock(stock: StockEntity) {
        stockDao.guardarStock(stock)
    }
}