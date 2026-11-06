package com.ucanscan.app.data.local

import androidx.room.Entity

// Stock de cada producto en cada sucursal.
@Entity(
    tableName = "stock_por_sucursal",
    primaryKeys = ["productoId", "sucursalId"]
)
data class StockEntity(
    val productoId: String,
    val sucursalId: String,
    val cantidad: Int,
    val stockMinimo: Int
)