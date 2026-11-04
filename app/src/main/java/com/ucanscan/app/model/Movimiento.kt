package com.ucanscan.app.model

// Registro de una entrada, salida o conteo de inventario.
data class Movimiento(
    val id: String,
    val productoId: String,
    val sucursalId: String,
    val usuarioId: String,
    val tipo: TipoMovimiento,
    val cantidad: Int,
    val fechaMillis: Long = System.currentTimeMillis(),
    val observacion: String = ""
)
