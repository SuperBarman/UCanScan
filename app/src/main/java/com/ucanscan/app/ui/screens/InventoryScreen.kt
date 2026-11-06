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
import androidx.compose.runtime.key
import androidx.compose.ui.tooling.preview.Preview
import com.ucanscan.app.ui.components.AppButton
import com.ucanscan.app.ui.components.AppTextField
import com.ucanscan.app.ui.theme.UCanScanTheme

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
                        onClick = { viewModel.cambiarSucursal(sucursal.id) },
                        label = { Text(sucursal.nombre) }
                    )
                }
            }
            AppTextField(
                value = viewModel.busqueda,
                onValueChange = viewModel::cambiarBusqueda,
                label = "Buscar por nombre o codigo"
            )
            if (productos.isEmpty()) {
                Text("No hay productos que coincidan con tu busqueda")
                AppButton(
                    text = "Limpiar Busqueda",
                    onClick = viewModel::limpiarBusqueda
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(productos, key = {it.producto.id }) { item ->
                        ProductCard(
                            item = item,
                            modifier = Modifier.animateItem())
                    }
                }

            }

        }
    }
}
@Preview(showBackground = true)
@Composable
fun InventoryScreenPreview(){
    UCanScanTheme {
        InventoryScreen(onBack = {})
    }
}
