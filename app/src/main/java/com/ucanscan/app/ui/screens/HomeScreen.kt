package com.ucanscan.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ucanscan.app.model.RolUsuario
import com.ucanscan.app.navigation.Routes
import com.ucanscan.app.navigation.puedeAcceder
import com.ucanscan.app.ui.components.AppButton
import com.ucanscan.app.ui.components.AppTopBar

@Composable
fun HomeScreen(
    rolSeleccionado: RolUsuario,
    onRoleChange: (RolUsuario) -> Unit,
    onOpenSection: (String) -> Unit,
    onLogout: () -> Unit
) {
    val roles = listOf(
        "Operador" to RolUsuario.OPERADOR,
        "Encargado de bodega" to RolUsuario.ENCARGADO_BODEGA,
        "Gerente" to RolUsuario.GERENTE
    )

    val secciones = listOf(
        "Inventario" to Routes.INVENTORY,
        "Escaner" to Routes.SCANNER,
        "Movimientos" to Routes.MOVEMENTS,
        "Control y alertas" to Routes.CONTROL,
        "Reportes" to Routes.REPORTS
    )

    Scaffold(
        topBar = { AppTopBar(title = "UCanScan") }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Menu principal",
                style = MaterialTheme.typography.headlineMedium
            )

            Text("Escanea, registra y controla.")

            Spacer(Modifier.height(12.dp))

            Text(
                "Seleccionar rol (demostracion)",
                style = MaterialTheme.typography.titleMedium
            )

            roles.forEach { (nombre, rol) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = rolSeleccionado == rol,
                        onClick = { onRoleChange(rol) }
                    )

                    Text(nombre)
                }
            }

            Spacer(Modifier.height(12.dp))

            Text(
                "Secciones disponibles",
                style = MaterialTheme.typography.titleMedium
            )

            secciones.forEach { (nombre, ruta) ->
                if (puedeAcceder(rolSeleccionado, ruta)) {
                    AppButton(
                        text = nombre,
                        onClick = { onOpenSection(ruta) }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al ingreso")
            }
        }
    }
}