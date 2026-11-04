package com.ucanscan.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ucanscan.app.navigation.AppNavHost
import com.ucanscan.app.ui.theme.UCanScanTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            UCanScanTheme {
                AppNavHost()
            }
        }
    }
}