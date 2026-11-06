package com.ucanscan.app.model

data class ProductoConStock(
    val producto: Producto,
    val cantidad: Int,
    val stockMinimo: Int
){
    val estado: EstadoStock
        get() = when{
            cantidad == 0 -> EstadoStock.AGOTADO
            cantidad <= stockMinimo -> EstadoStock.BAJO_MINIMO
            else -> EstadoStock.DISPONIBLE
        }
}
