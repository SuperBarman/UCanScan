package com.ucanscan.app.data

import com.ucanscan.app.model.Movimiento
import com.ucanscan.app.model.TipoMovimiento

// Datos ficticios para probar el historial.
object DatosControlEjemplo {

    val movimientos = listOf(
        Movimiento(
            id = "M1",
            productoId = "P1",
            sucursalId = "S1",
            usuarioId = "U1",
            tipo = TipoMovimiento.ENTRADA,
            cantidad = 5
        ),
        Movimiento(
            id = "M2",
            productoId = "P2",
            sucursalId = "S2",
            usuarioId = "U1",
            tipo = TipoMovimiento.SALIDA,
            cantidad = 2
        ),
        Movimiento(
            id = "M3",
            productoId = "P1",
            sucursalId = "S2",
            usuarioId = "U1",
            tipo = TipoMovimiento.CONTEO,
            cantidad = 3
        ),
        Movimiento(
            id = "M4",
            productoId = "P3",
            sucursalId = "S1",
            usuarioId = "U1",
            tipo = TipoMovimiento.CONTEO,
            cantidad = 1
        )
    )
}