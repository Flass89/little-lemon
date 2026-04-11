package com.example.littlelemon.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


// Little Lemon Brand Colors
val Green = Color(0xFF495E57)
val Yellow = Color(0xFFF4CE14)
val Cloud = Color(0xFFEDEFEE)
val Charcoal = Color(0xFF333333)

private val DarkColorPalette = darkColors(
    primary = Yellow,
    primaryVariant = Green,
    secondary = Cloud
)

private val LightColorPalette = lightColors(
    primary = Green,
    primaryVariant = Charcoal,
    secondary = Yellow

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun LittleLemonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Material 2 uses 'colors' and 'lightColors/darkColors'
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography, // Make sure Typography.kt is also Material 2
        shapes = Shapes,         // Make sure Shapes.kt is also Material 2
        content = content
    )
}