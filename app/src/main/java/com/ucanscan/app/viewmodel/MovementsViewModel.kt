
package com.ucanscan.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ucanscan.app.model.TipoMovimiento
//Crea una lista que Compose puede observar cuando cambia.
import androidx.compose.runtime.mutableStateListOf
//Utiliza el modelo creado por Mario.
import com.ucanscan.app.model.Movimiento

//Genera un identificador único para cada movimiento.
import java.util.UUID


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
    private val _movimientos = mutableStateListOf<Movimiento>()

    val movimientos: List<Movimiento>
        get() = _movimientos

    fun registrarMovimiento(
        tipo: TipoMovimiento,
        productoId: String,
        sucursalId: String,
        stockDisponible: Int
    ): Boolean {

        if (!validarCantidad(tipo, stockDisponible)) {
            return false
        }

        val numero = cantidad.toInt()

        val movimiento = Movimiento(
            id = UUID.randomUUID().toString(),
            productoId = productoId,
            sucursalId = sucursalId,
            usuarioId = "OPERADOR_DEMO",
            tipo = tipo,
            cantidad = numero
        )

        _movimientos.add(movimiento)
        cantidad = ""

        return true
    }
}
