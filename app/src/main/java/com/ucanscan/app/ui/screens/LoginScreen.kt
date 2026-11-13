package com.ucanscan.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.ucanscan.app.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucanscan.app.ui.components.AppButton
import com.ucanscan.app.ui.components.AppTextField
import com.ucanscan.app.viewmodel.LoginViewModel
import androidx.compose.ui.graphics.Color

@Composable
fun LoginScreen(
    onEnter: () -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {
    Scaffold (containerColor = Color.White) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(R.drawable.ucanscan_logo),
                contentDescription = "UCanScan",
                modifier = Modifier.fillMaxWidth().height(160.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(8.dp))

            Text("Escanea, registra y controla.")

            Spacer(Modifier.height(32.dp))

            // Campo de usuario
            AppTextField(
                value = loginViewModel.usuario,
                onValueChange = loginViewModel::cambiarUsuario,
                label = "Usuario"
            )

            Spacer(Modifier.height(16.dp))

            // Campo de contraseña
            AppTextField(
                value = loginViewModel.contrasena,
                onValueChange = loginViewModel::cambiarContrasena,
                label = "Contraseña",
                isPassword = true
            )

            Spacer(Modifier.height(16.dp))

            // Mostrar un error cuando exista
            if (loginViewModel.mensajeError.isNotEmpty()) {
                Text(
                    text = loginViewModel.mensajeError,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(Modifier.height(16.dp))
            }

            // Ingresar si el formulario es valido
            AppButton(
                text = "Ingresar",
                onClick = {
                    if (loginViewModel.validarIngreso()) {
                        onEnter()
                    }
                }
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Modo demostración.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}