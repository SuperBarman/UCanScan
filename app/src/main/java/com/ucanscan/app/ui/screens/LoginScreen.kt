package com.ucanscan.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(onEnter: () -> Unit) {

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "UCanScan",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text("Escanea, registra y controla.")

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = onEnter) {
                Text("Ingresar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Acceso de demostracion.")
        }
    }
}