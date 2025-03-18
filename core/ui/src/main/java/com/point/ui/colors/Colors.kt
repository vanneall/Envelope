package com.point.ui.colors

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)
val Black50 = Color(0x80000000)
val Gray = Color(0xFFB0B0B0)
val GrayLight = Color(0xFFE0E0E0)
val GrayDark = Color(0xFF606060)

val White = Color(0xFFFFFFFF)
val Red = Color(0xFFFF3333)
val Green = Color(0xFF4CAF50)
val Blue = Color(0xFF007ACC)

val BackgroundWhite = White
val SurfaceLight = Color(0xFFF5F5F5)

class EnvelopeColors(
    val primary: Color,
    val secondary: Color,
    val accent: Color,
    val success: Color,
    val error: Color,
    val background: Color,
    val surface: Color,
    val disabled: Color,
    val divider: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val overlay: Color,
)