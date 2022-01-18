package com.example.my_application.feature_movielist.presentation.utils

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = ColorPrimary,
    background = DarkRed,
    onBackground = TextWhite,
    onPrimary = DarkRed
)

private val LightColorPalette = lightColors(
    primary = ColorPrimary,
    background = Color.White,
    onBackground = MediumRed,
    onPrimary = DarkRed
)

@Composable
fun AppTheme(darkTheme: Boolean = true, content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}