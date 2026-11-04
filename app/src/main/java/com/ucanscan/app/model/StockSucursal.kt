package com.ucanscan.app.model

// El stock pertenece a un producto EN una sucursal concreta.
data class StockSucursal(
    val productoId: String,
    val sucursalId: String,
    val cantidad: Int,
    val stockMinimo: Int = 5
)
