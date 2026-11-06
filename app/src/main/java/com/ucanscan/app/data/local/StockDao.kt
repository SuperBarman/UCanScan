package com.ucanscan.app.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {

    // Obtener todos los registros y observar cambios.
    @Query("SELECT * FROM stock_por_sucursal")
    fun observarStock(): Flow<List<StockEntity>>

    // Buscar stock de un producto en una sucursal.
    @Query("""
        SELECT * FROM stock_por_sucursal
        WHERE productoId = :productoId
        AND sucursalId = :sucursalId
        LIMIT 1
    """)
    suspend fun buscarStock(
        productoId: String,
        sucursalId: String
    ): StockEntity?

    // Insertar o actualizar un registro.
    @Upsert
    suspend fun guardarStock(stock: StockEntity)
}