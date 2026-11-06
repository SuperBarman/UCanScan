package com.ucanscan.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucanscan.app.ui.components.AppTopBar
import com.ucanscan.app.ui.components.ProductCard
import com.ucanscan.app.viewmodel.InventoryViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import com.ucanscan.app.ui.components.AppTextField

// componente
@Composable
fun InventoryScreen(
    onBack: () -> Unit,
    viewModel: InventoryViewModel = viewModel()
) {
    val productos = viewModel.productosFiltrados()
    Scaffold(
        topBar = { AppTopBar(title = "Inventario", onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                viewModel.sucursales.forEach { sucursal ->
                    FilterChip(
                        selected = sucursal.id == viewModel.sucursalSeleccionadaId,
                        onClick = { viewModel.cambiarSucursal(sucursal.id)},
                        label = { Text(sucursal.nombre) }
                    )
                }
            }
            AppTextField(
                value = viewModel.busqueda,
                onValueChange = viewModel::cambiarBusqueda,
                label = "Buscar por nombre o codigo"
            )
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(productos) { item ->
                    ProductCard(item = item)
                }
            }

        }
    }
}

