package com.ucanscan.app.viewmodel

import  androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ucanscan.app.data.DatosEjemplo
import com.ucanscan.app.model.ProductoConStock
import com.ucanscan.app.repository.InventoryRepository


class InventoryViewModel : ViewModel() {
    private val repositorio = InventoryRepository()
    var busqueda by mutableStateOf("")
        private set

    val sucursales = DatosEjemplo.sucursales


    var sucursalSeleccionadaId by
    mutableStateOf(sucursales.first().id)
        private set


    fun cambiarBusqueda(texto: String) {
        busqueda = texto
    }

    fun cambiarSucursal(sucursalId: String) {
        sucursalSeleccionadaId = sucursalId
    }

    fun limpiarBusqueda() {
        busqueda = ""
    }

    fun productosFiltrados(): List<ProductoConStock> {
        val texto = busqueda.trim()
        return repositorio.productosConStock(sucursalSeleccionadaId)
            .filter { item ->
                texto.isEmpty() ||
                        item.producto.nombre.contains(texto, ignoreCase = true) ||
                        item.producto.codigoBarras.contains(texto, ignoreCase = true)

            }
    }


}