package com.ucanscan.app.navigation

import com.ucanscan.app.model.RolUsuario

// Define a que secciones puede acceder cada rol.
fun puedeAcceder(
    rol: RolUsuario,
    ruta: String
): Boolean {

    return when (rol) {

        RolUsuario.OPERADOR ->
            ruta == Routes.INVENTORY ||
            ruta == Routes.SCANNER ||
            ruta == Routes.MOVEMENTS

        RolUsuario.ENCARGADO_BODEGA ->
            ruta == Routes.INVENTORY ||
            ruta == Routes.SCANNER ||
            ruta == Routes.MOVEMENTS ||
            ruta == Routes.CONTROL

        RolUsuario.GERENTE ->
            ruta == Routes.INVENTORY ||
            ruta == Routes.CONTROL ||
            ruta == Routes.REPORTS
    }
}