package com.ucanscan.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ucanscan.app.ui.components.AppButton
import com.ucanscan.app.ui.components.AppTopBar

@Composable
fun SectionScreen(
    title: String,
    onBack: () -> Unit,
    permitido: Boolean = true
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = if (permitido) title else "Acceso restringido",
                onBack = onBack
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if (permitido) {
                Text("Funcionalidad pendiente de implementar.")
            } else {
                Text("Tu perfil no tiene permiso para abrir $title.")
            }

            Spacer(Modifier.height(24.dp))

            AppButton(
                text = "Volver al inicio",
                onClick = onBack
            )
        }
    }
}