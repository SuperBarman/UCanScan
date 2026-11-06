package com.ucanscan.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucanscan.app.ui.components.AppTopBar
import com.ucanscan.app.viewmodel.ReportsViewModel

@Composable
fun ReportsScreen(
    onBack: () -> Unit,
    reportsViewModel: ReportsViewModel = viewModel()
) {
    val general = reportsViewModel.obtenerResumenGeneral()
    val sucursales = reportsViewModel.obtenerResumenSucursales()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Reportes multitienda",
                onBack = onBack
            )
        }
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
                text = "Resumen general",
                style = MaterialTheme.typography.headlineSmall
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Productos: ${general.productos}")
                    Text("Sucursales: ${general.sucursales}")
                    Text("Unidades totales: ${general.unidades}")
                    Text("Alertas de stock: ${general.alertas}")
                    Text("Productos sin stock: ${general.sinStock}")
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Resumen por sucursal",
                style = MaterialTheme.typography.titleLarge
            )

            // Mostrar los datos de cada tienda.
            sucursales.forEach { sucursal ->

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = sucursal.nombre,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text("Unidades: ${sucursal.unidades}")
                        Text("Alertas: ${sucursal.alertas}")
                        Text("Sin stock: ${sucursal.sinStock}")
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Reportes calculados con datos de prueba.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}