package com.frontend.oportunia.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Primary400,
    onPrimary = Primary50,
    secondary = Primary300,
    onSecondary = Primary50,
    primaryContainer = Primary100,
    onPrimaryContainer = Primary900,

    background = Primary50,
    onBackground = Primary900,

    surface = Primary50,
    onSurface = Primary900,
)

private val DarkColorScheme = darkColorScheme(
    primary = Primary200,
    onPrimary = Primary900,
    primaryContainer = Primary500,
    onPrimaryContainer = Primary50,

    background = Primary900,
    onBackground = Primary50,

    surface = Primary500,
    onSurface = Primary50,
)


@Composable
fun OportunIATheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        shapes = Shapes
    )
}