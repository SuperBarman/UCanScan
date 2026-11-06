package com.ucanscan.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.ucanscan.app.repository.StockInicializador
import com.ucanscan.app.navigation.AppNavHost
import com.ucanscan.app.ui.theme.UCanScanTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            UCanScanTheme {
                                val context = LocalContext.current

                LaunchedEffect(Unit) {
                    StockInicializador(context).cargarSiVacio()
                }

                AppNavHost()
            }
        }
    }
}