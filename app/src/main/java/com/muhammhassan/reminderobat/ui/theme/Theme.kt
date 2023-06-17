package com.muhammhassan.reminderobat.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


//private val DarkColorPalette = darkColors(
//    primary = LightBlue,
//    primaryVariant = DarkBlue,
//    secondary = Teal200,
//    background = DarkBlue
//)

private val LightColorPalette = lightColors(
    primary = LightBlue,
    primaryVariant = DarkBlue,
    secondary = Teal200,
    background = DarkBlue
)

@Composable
fun ReminderObatTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
//    val colors = if (darkTheme) {
//        DarkColorPalette
//    } else {
//        LightColorPalette
//    }

    val colors = LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}