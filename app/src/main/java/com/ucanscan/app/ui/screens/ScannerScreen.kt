package com.ucanscan.app.ui.screens
// ============================================================
// IMPORTACIONES - JETPACK COMPOSE
// ============================================================

// ORGANIZACIÓN VISUAL
// Column organiza los elementos verticalmente.
import androidx.compose.foundation.layout.Column

// ELEMENTOS VISUALES
// Text muestra texto en la pantalla de la aplicación.
import androidx.compose.material3.Text

// FUNCIONES DE INTERFAZ
// @Composable permite crear funciones de interfaz gráfica.
import androidx.compose.runtime.Composable

//Crea un estado que, al cambiar, permite actualizar la interfaz
import androidx.compose.runtime.mutableStateOf

//Recuerda un valor durante las recomposiciones de la pantalla.
import androidx.compose.runtime.remember

// ============================================================
// IMPORTACIONES - COMPONENTES DE UCANSCAN
// ============================================================


//
import com.ucanscan.app.data.DatosEjemplo

//usar botones
import androidx.compose.material3.Button
// BARRA SUPERIOR
// AppTopBar reutiliza la barra superior creada por el equipo.
import com.ucanscan.app.ui.components.AppTopBar

//crea un campo de texto donde el usuario puede escribir
import androidx.compose.material3.OutlinedTextField

// ============================================================
// PANTALLA DEL ESCÁNER
// ============================================================
@Composable
fun ScannerScreen(onBack: () -> Unit) {

    val codigo = remember { mutableStateOf("") }
    var productoEncontrado = remember { mutableStateOf<String?>(null) }
    Column {
        AppTopBar(
            title = "Escáner",
            onBack = onBack
        )
        Text("Aquí escanearemos los productos")
        OutlinedTextField(
            value = codigo.value,
            onValueChange = {codigo.value = it},
            label = { Text("Codigo del producto")}
        )
        Button(
            onClick = {
                val producto = DatosEjemplo.productos.find {
                    it.codigoBarras.equals(codigo.value.trim(), ignoreCase = true)
                }

                productoEncontrado.value = producto?.nombre
            }
        ) {
            Text("Buscar producto")
        }

        // Mostrar resultado de la búsqueda
        if (productoEncontrado.value != null) {
            Text("Producto encontrado: ${productoEncontrado.value}")
        } else {
            Text("Todavía no hay un producto seleccionado")
        }

    } // Cierra Column
} // Cierra ScannerScreen