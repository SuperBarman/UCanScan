package com.ucanscan.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ucanscan.app.model.EstadoStock
import com.ucanscan.app.model.ProductoConStock
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning

@Composable
fun ProductCard(item: ProductoConStock, modifier: Modifier = Modifier){
    val colorEstado = when (item.estado){
        EstadoStock.DISPONIBLE -> Color.Green
        EstadoStock.BAJO_MINIMO -> Color.Yellow
        EstadoStock.AGOTADO -> Color.Red
    }

    val iconoEstado =  when (item.estado) {
        EstadoStock.DISPONIBLE -> Icons.Default.Check
        EstadoStock.BAJO_MINIMO -> Icons.Default.Warning
        EstadoStock.AGOTADO -> Icons.Default.Close
    }
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.producto.nombre,
                style = MaterialTheme.typography.titleMedium)
            Text(text = "Codigo: ${item.producto.codigoBarras}")
            Text(text = "Stock: ${item.cantidad} (minimo ${item.stockMinimo})")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = iconoEstado,
                    contentDescription = null,
                    tint = colorEstado
                )
                Text(text = item.estado.etiqueta, color = colorEstado)
            }
        }
    }
}