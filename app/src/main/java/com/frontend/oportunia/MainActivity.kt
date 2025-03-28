package com.frontend.oportunia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.frontend.oportunia.presentation.ui.screens.LoginScreen
import com.frontend.oportunia.presentation.ui.screens.MainScreen
import com.frontend.oportunia.presentation.ui.screens.RegisterScreen
import com.frontend.oportunia.presentation.ui.theme.OportunIATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OportunIATheme {
                Main()
            }
        }
    }
}


@Composable
fun Main() {

    Scaffold { paddingValues -> // Usamos Scaffold para envolver el contenido
        RegisterScreen(paddingValues) // Pasamos el paddingValues al LoginScreen
    }
}