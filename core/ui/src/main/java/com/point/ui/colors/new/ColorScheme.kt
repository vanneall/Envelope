package com.point.ui.colors.new

import androidx.compose.ui.graphics.Color

data class ColorTheme(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val outline: Color,
    val error: Color,
    val onError: Color
)

val LightColorTheme = ColorTheme(
    primary = Black,
    onPrimary = White,
    secondary = Gray200,
    onSecondary = Black400,
    tertiary = Coffee,
    onTertiary = OnCoffee,
    background = White,
    onBackground = Black,
    surface = Gray100,
    onSurface = Gray600,
    outline = Gray200,
    error = Red,
    onError = OnRed
)

