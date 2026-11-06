package com.ucanscan.app.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ucanscan.app.model.RolUsuario
import com.ucanscan.app.ui.screens.HomeScreen
import com.ucanscan.app.ui.screens.LoginScreen
import com.ucanscan.app.ui.screens.SectionScreen
import com.ucanscan.app.ui.screens.ReportsScreen
import com.ucanscan.app.ui.screens.ControlScreen

object Routes {
    const val LOGIN = "login"
    const val HOME = "home"
    const val INVENTORY = "inventory"
    const val SCANNER = "scanner"
    const val MOVEMENTS = "movements"
    const val CONTROL = "control"
    const val REPORTS = "reports"
}

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    // El rol se comparte entre el menu y la navegacion.
    var rolActual by remember {
        mutableStateOf(RolUsuario.OPERADOR)
    }

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {

        composable(Routes.LOGIN) {
            LoginScreen(
                onEnter = {
                    rolActual = RolUsuario.OPERADOR

                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                rolSeleccionado = rolActual,
                onRoleChange = { nuevoRol ->
                    rolActual = nuevoRol
                },
                onOpenSection = { ruta ->
                    // Comprobar permiso antes de navegar.
                    if (puedeAcceder(rolActual, ruta)) {
                        navController.navigate(ruta)
                    }
                },
                onLogout = {
                    rolActual = RolUsuario.OPERADOR

                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.INVENTORY) {
            SectionScreen(
                title = "Inventario",
                permitido = puedeAcceder(rolActual, Routes.INVENTORY),
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.SCANNER) {
            SectionScreen(
                title = "Escaner",
                permitido = puedeAcceder(rolActual, Routes.SCANNER),
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.MOVEMENTS) {
            SectionScreen(
                title = "Movimientos",
                permitido = puedeAcceder(rolActual, Routes.MOVEMENTS),
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.CONTROL) {
            if (puedeAcceder(rolActual, Routes.CONTROL)) {
                ControlScreen(
                    onBack = { navController.popBackStack() }
                )
            } else {
                SectionScreen(
                    title = "Control y alertas",
                    permitido = false,
                    onBack = { navController.popBackStack() }
                )
            }
        }

        composable(Routes.REPORTS) {
            if (puedeAcceder(rolActual, Routes.REPORTS)) {
                ReportsScreen(
                    onBack = { navController.popBackStack() }
                )
            } else {
                SectionScreen(
                    title = "Reportes",
                    permitido = false,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}