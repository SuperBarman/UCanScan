package com.ucanscan.app.data

import com.ucanscan.app.model.Producto
import com.ucanscan.app.model.StockSucursal
import com.ucanscan.app.model.Sucursal

// Datos de prueba temporales; mas adelante se reemplazaran por Room / API.
object DatosEjemplo {

    val sucursales = listOf(
        Sucursal(id = "S1", nombre = "Sucursal Centro"),
        Sucursal(id = "S2", nombre = "Sucursal Norte")
    )

    // DEMO-001, etc. son codigos de prueba, no codigos EAN reales.
    val productos = listOf(
        Producto(id = "P1", codigoBarras = "DEMO-001", nombre = "Teclado USB", categoria = "Perifericos"),
        Producto(id = "P2", codigoBarras = "DEMO-002", nombre = "Mouse inalambrico", categoria = "Perifericos"),
        Producto(id = "P3", codigoBarras = "DEMO-003", nombre = "Monitor 24 pulgadas", categoria = "Pantallas")
    )

    val stocks = listOf(
        StockSucursal(productoId = "P1", sucursalId = "S1", cantidad = 12, stockMinimo = 5),
        StockSucursal(productoId = "P1", sucursalId = "S2", cantidad = 2, stockMinimo = 5),
        StockSucursal(productoId = "P2", sucursalId = "S1", cantidad = 4, stockMinimo = 5),
        StockSucursal(productoId = "P2", sucursalId = "S2", cantidad = 15, stockMinimo = 5),
        StockSucursal(productoId = "P3", sucursalId = "S1", cantidad = 0, stockMinimo = 3),
        StockSucursal(productoId = "P3", sucursalId = "S2", cantidad = 9, stockMinimo = 3)
    )
}
