package com.ucanscan.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Colores generales de la aplicacion
private val UCanScanColors = lightColorScheme(
    primary = AzulUCanScan,
    onPrimary = BlancoUCanScan,

    secondary = AmarilloUCanScan,
    onSecondary = NegroUCanScan,

    background = FondoUCanScan,
    onBackground = NegroUCanScan,

    surface = BlancoUCanScan,
    onSurface = NegroUCanScan
)

@Composable
fun UCanScanTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = UCanScanColors,
        typography = Typography,
        content = content
    )
}