package com.ucanscan.app.model

// Cada tienda o bodega donde se controla el inventario.
data class Sucursal(
    val id: String,
    val nombre: String,
    val direccion: String = ""
)
