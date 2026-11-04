package com.ucanscan.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ucanscan.app.ui.screens.HomeScreen
import com.ucanscan.app.ui.screens.LoginScreen
import com.ucanscan.app.ui.screens.SectionScreen

// Nombres de las rutas
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

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {

        // Pantalla de ingreso
        composable(Routes.LOGIN) {
            LoginScreen(
                onEnter = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Menu principal
        composable(Routes.HOME) {
            HomeScreen(
                onOpenSection = { ruta ->
                    navController.navigate(ruta)
                },
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Pantallas provisionales
        composable(Routes.INVENTORY) {
            SectionScreen("Inventario") {
                navController.popBackStack()
            }
        }

        composable(Routes.SCANNER) {
            SectionScreen("Escaner") {
                navController.popBackStack()
            }
        }

        composable(Routes.MOVEMENTS) {
            SectionScreen("Movimientos") {
                navController.popBackStack()
            }
        }

        composable(Routes.CONTROL) {
            SectionScreen("Control y alertas") {
                navController.popBackStack()
            }
        }

        composable(Routes.REPORTS) {
            SectionScreen("Reportes") {
                navController.popBackStack()
            }
        }
    }
}