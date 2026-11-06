package com.ucanscan.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.ucanscan.app.data.DatosEjemplo
import com.ucanscan.app.ui.components.AppTopBar

@Composable
fun ScannerScreen(onBack: () -> Unit) {
    val contexto = LocalContext.current
    var codigo by rememberSaveable { mutableStateOf("") }
    var resultado by remember { mutableStateOf("Introduce un codigo o usa la camara.") }

    fun buscar(valor: String) {
        val encontrado = DatosEjemplo.productos.firstOrNull {
            it.codigoBarras.equals(valor.trim(), ignoreCase = true)
        }
        resultado = if (encontrado == null) "No se encontro un producto con ese codigo."
                    else "Producto encontrado: ${encontrado.nombre}"
    }

    Scaffold(topBar = { AppTopBar(title = "Escaner", onBack = onBack) }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Escanear codigo de barras", style = MaterialTheme.typography.headlineSmall)
            Button(
                onClick = {
                    GmsBarcodeScanning.getClient(contexto).startScan()
                        .addOnSuccessListener { barcode ->
                            val valor = barcode.rawValue.orEmpty()
                            codigo = valor
                            buscar(valor)
                        }
                        .addOnCanceledListener { resultado = "Escaneo cancelado." }
                        .addOnFailureListener { error ->
                            resultado = "No se pudo abrir la camara: ${error.message ?: "Revisa Google Play Services."}"
                        }
                }, modifier = Modifier.fillMaxWidth()
            ) { Text("Abrir camara y escanear") }

            OutlinedTextField(
                value = codigo,
                onValueChange = { codigo = it },
                label = { Text("Codigo del producto") },
                singleLine = true, modifier = Modifier.fillMaxWidth()
            )
            OutlinedButton(onClick = { buscar(codigo) }, modifier = Modifier.fillMaxWidth()) {
                Text("Buscar producto manualmente")
            }
            Text(resultado)
        }
    }
}