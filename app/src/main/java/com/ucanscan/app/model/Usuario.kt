package com.ucanscan.app.model

// Datos basicos de usuario. No almacenar contrasenas aqui.
data class Usuario(
    val id: String,
    val nombre: String,
    val correo: String,
    val rol: RolUsuario,
    val sucursalId: String? = null
)
