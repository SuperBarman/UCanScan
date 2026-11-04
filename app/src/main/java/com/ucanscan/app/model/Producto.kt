package com.ucanscan.app.model

// Un producto existe en el catalogo general de UCanScan.
data class Producto(
    val id: String,
    val codigoBarras: String,
    val nombre: String,
    val descripcion: String = "",
    val categoria: String = ""
)
