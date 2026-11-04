package com.ucanscan.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ucanscan.app.navigation.Routes

@Composable
fun HomeScreen(
    onOpenSection: (String) -> Unit,
    onLogout: () -> Unit
) {

    // Cada boton tiene un nombre y una ruta
    val secciones = listOf(
        "Inventario" to Routes.INVENTORY,
        "Escaner" to Routes.SCANNER,
        "Movimientos" to Routes.MOVEMENTS,
        "Control y alertas" to Routes.CONTROL,
        "Reportes" to Routes.REPORTS
    )

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "UCanScan",
                style = MaterialTheme.typography.headlineLarge
            )

            Text("Menu principal")

            Spacer(modifier = Modifier.height(16.dp))

            // Crear un boton por cada seccion
            secciones.forEach { (nombre, ruta) ->

                Button(
                    onClick = { onOpenSection(ruta) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(nombre)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al ingreso")
            }
        }
    }
}