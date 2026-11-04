package com.ucanscan.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SectionScreen(
    title: String,
    onBack: () -> Unit
) {

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Funcionalidad pendiente de implementar.")

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onBack) {
                Text("Volver al inicio")
            }
        }
    }
}