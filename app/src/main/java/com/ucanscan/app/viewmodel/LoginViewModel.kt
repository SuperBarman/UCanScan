package com.ucanscan.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    var usuario by mutableStateOf("")
        private set

    var contrasena by mutableStateOf("")
        private set

    var mensajeError by mutableStateOf("")
        private set

    // Actualiza el usuario escrito
    fun cambiarUsuario(nuevoUsuario: String) {
        usuario = nuevoUsuario
        mensajeError = ""
    }

    // Actualiza la contraseña escrita
    fun cambiarContrasena(nuevaContrasena: String) {
        contrasena = nuevaContrasena
        mensajeError = ""
    }

    // Comprueba los campos antes de ingresar
    fun validarIngreso(): Boolean {

        mensajeError = when {
            usuario.isBlank() ->
                "Debes ingresar tu usuario."

            contrasena.isBlank() ->
                "Debes ingresar tu contraseña."

            contrasena.length < 6 ->
                "La contraseña debe tener al menos 6 caracteres."

            else -> ""
        }

        return mensajeError.isEmpty()
    }
}