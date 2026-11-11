package com.ucanscan.app.ui.components

import android.net.Uri
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

/**
 * Evidencia de un conteo. Se muestra como vista previa, sin guardarse en Room.
 * Android Photo Picker permite seleccionar fotos sin pedir permiso de galeria.
 */
@Composable
fun EvidenciaConteoPanel() {
    var fotoUri by remember { mutableStateOf<Uri?>(null) }
    var fotoVisible by remember { mutableStateOf(false) }

    val elegirFoto = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            fotoUri = uri
            fotoVisible = true
        }
    }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                "Evidencia fotografica (opcional)",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                "Si hay diferencias en el conteo, puedes adjuntar una foto " +
                    "para mostrarla como evidencia."
            )

            Button(
                onClick = {
                    elegirFoto.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Seleccionar foto de la galeria")
            }

            // Animacion: la vista previa aparece/desaparece suavemente.
            AnimatedVisibility(
                visible = fotoVisible,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                val uri = fotoUri
                if (uri != null) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        AndroidView(
                            factory = { contexto ->
                                ImageView(contexto).apply {
                                    scaleType = ImageView.ScaleType.CENTER_CROP
                                }
                            },
                            update = { imagen -> imagen.setImageURI(uri) },
                            modifier = Modifier.fillMaxWidth().height(180.dp)
                        )
                        Text(
                            "Vista previa: imagen seleccionada del dispositivo.",
                            style = MaterialTheme.typography.bodySmall
                        )
                        OutlinedButton(onClick = { fotoVisible = false }) {
                            Text("Ocultar foto")
                        }
                    }
                }
            }
            Text(
                "Demostracion: la foto no se guarda en la base de datos.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}