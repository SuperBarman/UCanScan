
package com.ucanscan.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ucanscan.app.model.TipoMovimiento

class MovementsViewModel : ViewModel() {

    // Guarda la cantidad ingresada.
    var cantidad by mutableStateOf("")
        private set

    // Guarda el mensaje de error.
    var mensajeError by mutableStateOf("")
        private set

    // Actualiza la cantidad escrita.
    fun cambiarCantidad(nuevaCantidad: String) {
        cantidad = nuevaCantidad
        mensajeError = ""
    }

    // Comprueba que la cantidad sea válida.

    fun validarCantidad(
        tipo: TipoMovimiento,
        stockDisponible: Int
    ): Boolean {

        val numero = cantidad.toIntOrNull()

        mensajeError = when {
            numero == null ->
                "Debes ingresar un número entero."

            numero < 0 ->
                "La cantidad no puede ser negativa."

            numero == 0 && tipo != TipoMovimiento.CONTEO ->
                "La cantidad debe ser mayor a cero."

            tipo == TipoMovimiento.SALIDA && numero > stockDisponible ->
                "No tienes suficiente stock disponible."

            else -> ""
        }

        return mensajeError.isEmpty()
    }

}
